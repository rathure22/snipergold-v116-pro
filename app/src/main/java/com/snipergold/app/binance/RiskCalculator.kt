
package com.snipergold.app.binance
import kotlin.math.abs
object RiskCalculator {
    const val FIXED_LOT = 0.01
    const val RISK_PERCENT = 1.0
    fun calculateSLTP(entry: Double, atr: Double, side: String): Pair<Double,Double> {
        val slDist = atr * 1.5
        val tpDist = slDist * 2.0
        return if(side=="BUY") Pair(entry-slDist, entry+tpDist) else Pair(entry+slDist, entry-tpDist)
    }
}
