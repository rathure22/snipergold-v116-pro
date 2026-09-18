
package com.snipergold.app.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.snipergold.app.binance.TradeHistoryStorage
@Composable
fun HistoryCheckScreen(storage: TradeHistoryStorage, livePrice: Double) {
    val trades = storage.getAllTrades()
    Column(Modifier.fillMaxSize().padding(12.dp), verticalArrangement=Arrangement.spacedBy(8.dp)) {
        Text(storage.getStats(), color=Color(0xFFFFD700))
        LazyColumn(verticalArrangement=Arrangement.spacedBy(4.dp)) {
            items(trades) { item ->
                val (isNigo, msg) = storage.checkIfNigo(item.entryPrice, item.sl, item.tp, item.side, livePrice)
                val bg = when(isNigo){ true->Color(0xFF0A2F1A); false->Color(0xFF2F0A0A); null->Color(0xFF2F2F0A) }
                Card(colors=CardDefaults.cardColors(bg)) {
                    Row(Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement=Arrangement.SpaceBetween) {
                        Column { Text("${item.side} 0.01 ${item.symbol}", color=Color.White); Text("Entry ${item.entryPrice} SL ${item.sl} TP ${item.tp}", color=Color.Gray); Text("TP SMART: ${item.tpSmartStage}", color=Color(0xFFFFD700)) }
                        Column { Text(when(isNigo){true->"✓ NIGO"; false->"X PILDI"; null->"● LIVE"}, color=when(isNigo){true->Color.Green; false->Color.Red; null->Color.Yellow}); Text(msg, color=Color.LightGray) }
                    }
                }
            }
        }
    }
}
