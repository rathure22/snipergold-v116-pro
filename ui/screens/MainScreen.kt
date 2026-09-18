
package com.snipergold.app.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snipergold.app.ui.MainViewModel
@Composable
fun MainScreen(vm: MainViewModel = viewModel()) {
    val state by vm.uiState.collectAsState()
    LaunchedEffect(Unit){ vm.startLiveGoldFeed(); vm.checkBinanceConnection() }
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Card(colors=CardDefaults.cardColors(Color(0xFF1A1A1A))) {
            Column(Modifier.padding(12.dp)) {
                Text("Gold: $${state.goldPrice} - ${state.mode}", color=Color(0xFFFFD700))
                Text("TP SMART: ${state.tpSmartMsg}", color=Color.Green)
                Text(state.message, color=Color.Gray)
            }
        }
        Row(horizontalArrangement=Arrangement.spacedBy(12.dp)) {
            Button(onClick={vm.placeRealOrder("BUY")}, modifier=Modifier.weight(1f), colors=ButtonDefaults.buttonColors(Color(0xFF00FF88))) { Text("BUY 0.01", color=Color.Black) }
            Button(onClick={vm.placeRealOrder("SELL")}, modifier=Modifier.weight(1f), colors=ButtonDefaults.buttonColors(Color(0xFFFF4444))) { Text("SELL 0.01", color=Color.White) }
        }
        Text("Balance: ${state.balance} | Connected: ${state.isConnected}", color=Color.White)
        Text("Source: gold-api.com FREE NO KEY + Binance optional", color=Color.Gray)
    }
}
