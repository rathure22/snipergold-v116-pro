
package com.snipergold.app

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.snipergold.app.ui.theme.SniperGoldTheme

// TINUOD NA APP - WebView wrapper para sa HTML nga mahimong APK
class MainActivityWebView: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SniperGoldTheme {
                GoldWebView()
            }
        }
    }
}

@Composable
fun GoldWebView() {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                // Load imong HTML nga LIVE app
                // Option 1: Load from assets
                // loadUrl("file:///android_asset/snipergold_live.html")
                // Option 2: Load live PWA
                loadUrl("https://api.gold-api.com/price/XAU")
                // Para sa imong HTML nga gihimo nato, i-copy sa app/src/main/assets/
            }
        }
    )
}
