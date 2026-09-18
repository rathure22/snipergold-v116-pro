package com.snipergold.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.snipergold.app.ui.AdaptiveShell
import com.snipergold.app.ui.theme.SniperGoldTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SniperGoldTheme {
                AdaptiveShell()
            }
        }
    }
}
