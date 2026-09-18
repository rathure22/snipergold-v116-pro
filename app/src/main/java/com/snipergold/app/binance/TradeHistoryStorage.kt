package com.snipergold.app.binance

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.math.abs

class TradeHistoryStorage(context: Context) {
    private val prefs = context.getSharedPreferences("trade_history", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveAll(list: List<TradeHistoryItem>) {
        prefs.edit().putString("trades", gson.toJson(list)).apply()
    }

    fun saveTrade(item: TradeHistoryItem) {
        val list = getAllTrades().toMutableList()
        list.add(0, item)
        if (list.size > 200) list.subList(200, list.size).clear()
        saveAll(list)
    }

    fun saveSimulatedTrade(side: String, entry: Double, sl: Double, tp: Double, live: Double) {
        saveTrade(
            TradeHistoryItem(
                orderId = "SIM_${System.currentTimeMillis()}",
                side = side,
                entryPrice = entry,
                sl = sl,
                tp = tp,
                currentPrice = live,
                status = "LIVE",
                mode = "FREE"
            )
        )
    }

    fun getAllTrades(): List<TradeHistoryItem> {
        val json = prefs.getString("trades", "[]") ?: "[]"
        val type = object : TypeToken<List<TradeHistoryItem>>() {}.type
        return try {
            gson.fromJson(json, type) ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun checkIfNigo(
        entry: Double,
        sl: Double,
        tp: Double,
        side: String,
        livePrice: Double
    ): Pair<Boolean?, String> {
        val isBuy = side == "BUY"
        return when {
            isBuy && livePrice >= tp -> Pair(true, "✓ NIGO TP")
            isBuy && livePrice <= sl -> Pair(false, "X PILDI SL")
            !isBuy && livePrice <= tp -> Pair(true, "✓ NIGO TP")
            !isBuy && livePrice >= sl -> Pair(false, "X PILDI SL")
            else -> {
                val pct = try {
                    ((livePrice - entry) / (tp - entry) * 100).toInt()
                } catch (e: Exception) {
                    0
                }
                Pair(null, "LIVE $pct% - Gapapa")
            }
        }
    }

    fun updateLiveTrade(orderId: String, livePrice: Double, stage: String) {
        val list = getAllTrades().toMutableList()
        val idx = list.indexOfFirst { it.orderId == orderId }
        if (idx != -1) {
            val old = list[idx]
            val (nigo, _) = checkIfNigo(old.entryPrice, old.sl, old.tp, old.side, livePrice)
            val newStatus = when (nigo) {
                true -> "WIN_✓"
                false -> "LOSS_X"
                null -> "LIVE"
            }
            list[idx] = old.copy(
                currentPrice = livePrice,
                isNigo = nigo,
                status = newStatus,
                tpSmartStage = stage
            )
            saveAll(list)
        }
    }

    fun getStats(): String {
        val all = getAllTrades()
        val w = all.count { it.status.contains("WIN") }
        val l = all.count { it.status.contains("LOSS") }
        val wr = if (all.isNotEmpty()) w * 100 / all.size else 0
        return "✓ $w | X $l | $wr% WR | Total: ${all.size}"
    }

    fun getDailyLoss(): Double =
        getAllTrades()
            .filter {
                it.timestamp > System.currentTimeMillis() - 86_400_000 &&
                    it.status.contains("LOSS")
            }
            .sumOf { abs(it.pnl) }
}
