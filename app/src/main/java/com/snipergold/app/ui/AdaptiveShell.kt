
package com.snipergold.app.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.snipergold.app.ui.screens.BinanceSidebarSection
import com.snipergold.app.ui.screens.HistoryCheckScreen
import com.snipergold.app.ui.screens.MainScreen
@Composable
fun AdaptiveShell() {
    var selectedTab by remember { mutableStateOf(0) }
    Scaffold(bottomBar = { NavigationBar { NavigationBarItem(selected=selectedTab==0, onClick={selectedTab=0}, label={Text("Sniper")}, icon={Text("🎯")}); NavigationBarItem(selected=selectedTab==1, onClick={selectedTab=1}, label={Text("History ✓/X")}, icon={Text("📜")}); NavigationBarItem(selected=selectedTab==2, onClick={selectedTab=2}, label={Text("Keys")}, icon={Text("🔑")}) } }) { pad ->
        Box(Modifier.padding(pad).padding(12.dp)) {
            when(selectedTab) {
                0 -> MainScreen()
                1 -> Text("History Screen - i-connect sa TradeHistoryStorage")
                2 -> Text("Binance Keys - Paste Ready")
            }
        }
    }
}
