package com.snipergold.app.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.snipergold.app.ui.screens.BinanceSidebarSection
import com.snipergold.app.ui.screens.HistoryCheckScreen
import com.snipergold.app.ui.screens.MainScreen

@Composable
fun AdaptiveShell(vm: MainViewModel = viewModel()) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val state by vm.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    label = { Text("Sniper") },
                    icon = { Text("🎯") }
                )
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    label = { Text("History ✓/X") },
                    icon = { Text("📜") }
                )
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    label = { Text("Keys") },
                    icon = { Text("🔑") }
                )
            }
        }
    ) { pad ->
        Box(
            Modifier
                .padding(pad)
                .padding(12.dp)
                .fillMaxSize()
        ) {
            when (selectedTab) {
                0 -> MainScreen(vm)
                1 -> HistoryCheckScreen(
                    storage = vm.tradeStorage,
                    livePrice = state.goldPrice
                )
                2 -> BinanceSidebarSection(
                    storage = vm.storage,
                    onKeysChanged = { vm.onKeysSaved() }
                )
            }
        }
    }
}
