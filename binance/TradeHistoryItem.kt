
package com.snipergold.app.binance
data class TradeHistoryItem(
    val orderId: String,
    val symbol: String = "PAXGUSDT",
    val side: String,
    val qty: Double = 0.01,
    val entryPrice: Double,
    val sl: Double,
    val tp: Double,
    val currentPrice: Double = 0.0,
    val status: String = "LIVE",
    val pnl: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis(),
    val tpSmartStage: String = "WAITING",
    val isNigo: Boolean? = null,
    val mode: String = "FREE" // FREE or REAL
)
