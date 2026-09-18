
package com.snipergold.app.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.snipergold.app.binance.SecureApiStorage
@Composable
fun BinanceSidebarSection(storage: SecureApiStorage) {
    var apiKey by remember { mutableStateOf(storage.getApiKey()) }
    var secret by remember { mutableStateOf("") }
    Card(Modifier.fillMaxWidth(), colors=CardDefaults.cardColors(Color(0xFF1A1A1A))) {
        Column(Modifier.padding(12.dp), verticalArrangement=Arrangement.spacedBy(8.dp)) {
            Text("Binance Keys (OPTIONAL - Ready to Paste)", color=Color(0xFFFFD700))
            Text(storage.getMode(), color=if(storage.hasKeys()) Color.Green else Color.Yellow)
            OutlinedTextField(value=apiKey, onValueChange={apiKey=it}, label={Text("API KEY")}, placeholder={Text("Paste API Key")}, modifier=Modifier.fillMaxWidth(), singleLine=true)
            OutlinedTextField(value=secret, onValueChange={secret=it}, label={Text("SECRET")}, placeholder={Text("Paste Secret")}, modifier=Modifier.fillMaxWidth(), singleLine=true, visualTransformation=PasswordVisualTransformation())
            Row(horizontalArrangement=Arrangement.spacedBy(8.dp)) {
                Button(onClick={storage.saveKeys(apiKey, secret)}, colors=ButtonDefaults.buttonColors(Color(0xFFFFD700))) { Text("SAVE & CONNECT", color=Color.Black) }
                OutlinedButton(onClick={storage.clearKeys(); apiKey=""; secret=""}) { Text("CLEAR -> FREE MODE") }
            }
        }
    }
}
