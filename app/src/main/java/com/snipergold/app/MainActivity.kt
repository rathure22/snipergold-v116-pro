
package com.snipergold.app
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.snipergold.app.ui.AdaptiveShell
import com.snipergold.app.ui.theme.SniperGoldTheme
class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { SniperGoldTheme { AdaptiveShell() } }
    }
}
