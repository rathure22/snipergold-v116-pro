
package com.snipergold.app.ui
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snipergold.app.binance.*
import com.snipergold.app.data.GoldApiManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
data class MainUiState(val balance: String="0", val goldPrice: Double=0.0, val isConnected: Boolean=false, val mode: String="FREE MODE - gold-api.com", val message: String="Waiting...", val tpSmartMsg: String="")
class MainViewModel(
    private val storage: SecureApiStorage? = null,
    private val binanceManager: BinanceManager? = null,
    private val tradeStorage: TradeHistoryStorage? = null
): ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState
    private val goldApi = GoldApiManager
    private val signalDetector = GoldSignalDetector()
    private val tpSmart = TpSmartManager()
    fun fetchBalance() { _uiState.value = _uiState.value.copy(balance="0.01 lot ready") }
    fun checkBinanceConnection() {
        val hasKeys = storage?.hasKeys() ?: false
        if(!hasKeys) { _uiState.value = _uiState.value.copy(isConnected=false, mode="FREE MODE - gold-api.com (No Key)", message="FREE: Gold price live, simulation ✓/X"); return }
        viewModelScope.launch { val ok = binanceManager?.checkConnection() ?: false; _uiState.value = _uiState.value.copy(isConnected=ok, mode=if(ok)"REAL TRADE READY ✓" else "KEY ERROR") }
    }
    fun startLiveGoldFeed() {
        viewModelScope.launch {
            while(true) {
                val gold = goldApi.getLiveGoldPrice()
                if(gold!=null) {
                    _uiState.value = _uiState.value.copy(goldPrice=gold.price)
                    maybeAutoTradeBinance(gold.price)
                }
                delay(10000)
            }
        }
    }
    fun maybeAutoTradeBinance(livePrice: Double) {
        val signal = signalDetector.checkSignal(livePrice) ?: return
        val tpStatus = tpSmart.checkAndUpdateTpSmart(signal.entry, signal.sl, signal.tp, livePrice, signal.side)
        _uiState.value = _uiState.value.copy(tpSmartMsg=tpStatus.message)
        // Save check record
        tradeStorage?.saveSimulatedTrade(signal.side, signal.entry, signal.sl, signal.tp, livePrice)
    }
    fun placeRealOrder(side: String) {
        val price = _uiState.value.goldPrice
        val (sl,tp) = RiskCalculator.calculateSLTP(price, 3.0, side)
        if(storage?.hasKeys()==true) { binanceManager?.placeRealOrder("PAXGUSDT", side, 0.01, sl, tp) }
        tradeStorage?.saveSimulatedTrade(side, price, sl, tp, price)
    }
}
