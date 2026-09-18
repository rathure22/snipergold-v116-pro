package com.snipergold.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.snipergold.app.binance.TradeHistoryStorage

@Composable
fun HistoryCheckScreen(storage: TradeHistoryStorage, livePrice: Double) {
    val trades = storage.getAllTrades()

    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(storage.getStats(), color = Color(0xFFFFD700))

        if (trades.isEmpty()) {
            Text("No trades yet. BUY/SELL or wait for signal.", color = Color.Gray)
        }

        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(trades, key = { it.orderId }) { item ->
                val (isNigo, msg) = storage.checkIfNigo(
                    item.entryPrice, item.sl, item.tp, item.side, livePrice
                )
                val bg = when (isNigo) {
                    true -> Color(0xFF0A2F1A)
                    false -> Color(0xFF2F0A0A)
                    null -> Color(0xFF2F2F0A)
                }
                Card(colors = CardDefaults.cardColors(bg)) {
                    Row(
                        Modifier
                            .padding(12.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                "${item.side} 0.01 ${item.symbol}",
                                color = Color.White
                            )
                            Text(
                                "Entry ${"%.2f".format(item.entryPrice)} SL ${"%.2f".format(item.sl)} TP ${"%.2f".format(item.tp)}",
                                color = Color.Gray
                            )
                            Text(
                                "TP SMART: ${item.tpSmartStage}",
                                color = Color(0xFFFFD700)
                            )
                        }
                        Column {
                            Text(
                                when (isNigo) {
                                    true -> "✓ NIGO"
                                    false -> "X PILDI"
                                    null -> "● LIVE"
                                },
                                color = when (isNigo) {
                                    true -> Color(0xFF00FF88)
                                    false -> Color(0xFFFF4444)
                                    null -> Color(0xFFFFD700)
                                }
                            )
                            Text(msg, color = Color.LightGray)
                        }
                    }
                }
            }
        }
    }
}
