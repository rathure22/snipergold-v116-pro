package com.snipergold.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.snipergold.app.binance.SecureApiStorage

@Composable
fun BinanceSidebarSection(
    storage: SecureApiStorage,
    onKeysChanged: () -> Unit = {}
) {
    var apiKey by remember { mutableStateOf(storage.getApiKey()) }
    var secret by remember { mutableStateOf("") }
    var statusMsg by remember { mutableStateOf("") }

    Card(
        Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color(0xFF1A1A1A))
    ) {
        Column(
            Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Binance Keys (OPTIONAL - Ready to Paste)", color = Color(0xFFFFD700))
            Text(
                storage.getMode(),
                color = if (storage.hasKeys()) Color(0xFF00FF88) else Color(0xFFFFD700)
            )

            OutlinedTextField(
                value = apiKey,
                onValueChange = { apiKey = it },
                label = { Text("API KEY") },
                placeholder = { Text("Paste API Key") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            OutlinedTextField(
                value = secret,
                onValueChange = { secret = it },
                label = { Text("SECRET") },
                placeholder = { Text("Paste Secret") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = {
                        storage.saveKeys(apiKey, secret)
                        statusMsg = if (storage.hasKeys()) "Keys saved ✓" else "Keys incomplete"
                        onKeysChanged()
                    },
                    colors = ButtonDefaults.buttonColors(Color(0xFFFFD700))
                ) {
                    Text("SAVE & CONNECT", color = Color.Black)
                }
                OutlinedButton(
                    onClick = {
                        storage.clearKeys()
                        apiKey = ""
                        secret = ""
                        statusMsg = "Cleared → FREE MODE"
                        onKeysChanged()
                    }
                ) {
                    Text("CLEAR → FREE")
                }
            }

            if (statusMsg.isNotBlank()) {
                Text(statusMsg, color = Color(0xFF00FF88))
            }

            Text(
                "Tip: Use read-only or restricted API key for safety. Real orders only when keys valid.",
                color = Color.Gray
            )
        }
    }
}
