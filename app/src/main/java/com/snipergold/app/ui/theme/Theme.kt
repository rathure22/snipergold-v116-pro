
package com.snipergold.app.ui.theme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
private val Gold = Color(0xFFFFD700)
private val Dark = Color(0xFF0A0A0A)
private val DarkSurface = Color(0xFF1A1A1A)
private val DarkScheme = darkColorScheme(primary = Gold, background = Dark, surface = DarkSurface, onBackground = Color.White)
@Composable fun SniperGoldTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = DarkScheme, content = content)
}
