package com.snipergold.app.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.snipergold.app.binance.BinanceManager
import com.snipergold.app.binance.GoldSignalDetector
import com.snipergold.app.binance.RiskCalculator
import com.snipergold.app.binance.SecureApiStorage
import com.snipergold.app.binance.TpSmartManager
import com.snipergold.app.binance.TradeHistoryStorage
import com.snipergold.app.data.GoldApiManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MainUiState(
    val balance: String = "0",
    val goldPrice: Double = 0.0,
    val isConnected: Boolean = false,
    val mode: String = "FREE MODE - gold-api.com",
    val message: String = "Waiting for gold price...",
    val tpSmartMsg: String = "",
    val lastUpdate: Long = 0L
)

class MainViewModel(application: Application) : AndroidViewModel(application) {

    val storage = SecureApiStorage(application)
    val tradeStorage = TradeHistoryStorage(application)
    private val binanceManager = BinanceManager(storage)

    private val goldApi = GoldApiManager
    private val signalDetector = GoldSignalDetector()
    private val tpSmart = TpSmartManager()

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        fetchBalance()
        checkBinanceConnection()
        startLiveGoldFeed()
    }

    fun fetchBalance() {
        _uiState.value = _uiState.value.copy(balance = "0.01 lot ready")
    }

    fun checkBinanceConnection() {
        val hasKeys = storage.hasKeys()
        if (!hasKeys) {
            _uiState.value = _uiState.value.copy(
                isConnected = false,
                mode = "FREE MODE - gold-api.com (No Key)",
                message = "FREE: Gold price live, simulation ✓/X"
            )
            return
        }
        viewModelScope.launch {
            val ok = binanceManager.checkConnection()
            _uiState.value = _uiState.value.copy(
                isConnected = ok,
                mode = if (ok) "REAL TRADE READY ✓" else "KEY ERROR - check keys",
                message = if (ok) "Binance connected" else "Invalid API keys"
            )
        }
    }

    fun startLiveGoldFeed() {
        viewModelScope.launch {
            while (true) {
                try {
                    val gold = goldApi.getLiveGoldPrice()
                    if (gold != null) {
                        _uiState.value = _uiState.value.copy(
                            goldPrice = gold.price,
                            lastUpdate = System.currentTimeMillis(),
                            message = "Live from gold-api.com"
                        )
                        maybeAutoTradeBinance(gold.price)
                    } else {
                        _uiState.value = _uiState.value.copy(message = "Price fetch failed, retrying...")
                    }
                } catch (e: Exception) {
                    _uiState.value = _uiState.value.copy(message = "Network error: ${e.message}")
                }
                delay(10_000)
            }
        }
    }

    private fun maybeAutoTradeBinance(livePrice: Double) {
        val signal = signalDetector.checkSignal(livePrice) ?: return
        val tpStatus = tpSmart.checkAndUpdateTpSmart(
            signal.entry, signal.sl, signal.tp, livePrice, signal.side
        )
        _uiState.value = _uiState.value.copy(tpSmartMsg = tpStatus.message)
        tradeStorage.saveSimulatedTrade(signal.side, signal.entry, signal.sl, signal.tp, livePrice)
    }

    fun placeRealOrder(side: String) {
        val price = _uiState.value.goldPrice
        if (price <= 0) {
            _uiState.value = _uiState.value.copy(message = "No live price yet")
            return
        }
        val (sl, tp) = RiskCalculator.calculateSLTP(price, 3.0, side)
        if (storage.hasKeys()) {
            val orderId = binanceManager.placeRealOrder("PAXGUSDT", side, 0.01, sl, tp)
            _uiState.value = _uiState.value.copy(
                message = if (orderId != null) "Order placed: $orderId" else "Order failed (check keys/network)"
            )
        } else {
            _uiState.value = _uiState.value.copy(message = "Simulated $side @ $price (FREE MODE)")
        }
        tradeStorage.saveSimulatedTrade(side, price, sl, tp, price)
    }

    fun onKeysSaved() {
        checkBinanceConnection()
        fetchBalance()
    }
}
