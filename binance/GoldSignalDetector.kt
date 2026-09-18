
package com.snipergold.app.binance
data class Signal(val side: String, val entry: Double, val sl: Double, val tp: Double, val atr: Double)
class GoldSignalDetector {
    // Simple EMA 50/200 + RSI 40-50 pullback logic
    fun checkSignal(livePrice: Double, ema50: Double = livePrice-1, ema200: Double = livePrice-3, rsi: Double = 45.0): Signal? {
        val atr = 3.0 // kuhaon sa history, static sa karon para simple
        if(livePrice > ema50 && ema50 > ema200 && rsi in 40.0..55.0) {
            val (sl,tp) = RiskCalculator.calculateSLTP(livePrice, atr, "BUY")
            return Signal("BUY", livePrice, sl, tp, atr)
        }
        if(livePrice < ema50 && ema50 < ema200 && rsi in 45.0..60.0) {
            val (sl,tp) = RiskCalculator.calculateSLTP(livePrice, atr, "SELL")
            return Signal("SELL", livePrice, sl, tp, atr)
        }
        return null
    }
}
