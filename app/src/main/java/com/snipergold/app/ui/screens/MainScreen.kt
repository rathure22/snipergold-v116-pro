package com.snipergold.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.snipergold.app.ui.MainViewModel

@Composable
fun MainScreen(vm: MainViewModel) {
    val state by vm.uiState.collectAsState()

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(colors = CardDefaults.cardColors(Color(0xFF1A1A1A))) {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    "Gold: $${"%.2f".format(state.goldPrice)} - ${state.mode}",
                    color = Color(0xFFFFD700)
                )
                Text("TP SMART: ${state.tpSmartMsg.ifBlank { "Waiting signal..." }}", color = Color(0xFF00FF88))
                Text(state.message, color = Color.Gray)
            }
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { vm.placeRealOrder("BUY") },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color(0xFF00FF88))
            ) {
                Text("BUY 0.01", color = Color.Black)
            }
            Button(
                onClick = { vm.placeRealOrder("SELL") },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(Color(0xFFFF4444))
            ) {
                Text("SELL 0.01", color = Color.White)
            }
        }

        Text(
            "Balance: ${state.balance} | Connected: ${state.isConnected}",
            color = Color.White
        )
        Text(
            "Source: gold-api.com FREE NO KEY + Binance optional",
            color = Color.Gray
        )
    }
}
