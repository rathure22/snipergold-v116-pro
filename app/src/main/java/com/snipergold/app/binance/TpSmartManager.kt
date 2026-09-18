
package com.snipergold.app.binance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.math.abs
data class TpSmartStatus(val stage: String, val currentSL: Double, val currentTP: Double, val message: String)
class TpSmartManager {
    private val _status = MutableStateFlow<TpSmartStatus?>(null)
    val status: StateFlow<TpSmartStatus?> = _status
    fun checkAndUpdateTpSmart(entry: Double, sl: Double, tp: Double, currentPrice: Double, side: String): TpSmartStatus {
        val isBuy = side=="BUY"
        val tpDist = abs(tp-entry)
        val curDist = if(isBuy) currentPrice-entry else entry-currentPrice
        val pct = if(tpDist>0) curDist/tpDist*100 else 0.0
        return when {
            pct >= 100 -> TpSmartStatus("CLOSED_TP", sl, tp, "✓ NIGO TP ${pct.toInt()}%")
            pct >= 80 -> {
                val newSL = if(isBuy) currentPrice-1.5 else currentPrice+1.5
                TpSmartStatus("TRAILING", newSL, tp, "TRAILING ${pct.toInt()}%")
            }
            pct >= 50 -> {
                val be = if(isBuy) entry+0.3 else entry-0.3
                TpSmartStatus("BE_ACTIVATED", be, tp, "BE+ SAFE 50% nigo")
            }
            pct <= -100 -> TpSmartStatus("CLOSED_SL", sl, tp, "X PILDI SL")
            else -> TpSmartStatus("LIVE", sl, tp, "● LIVE ${pct.toInt()}%")
        }.also{_status.value=it}
    }
}
