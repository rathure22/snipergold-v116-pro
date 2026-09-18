
package com.snipergold.app.binance
import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
class SecureApiStorage(context: Context) {
    private val masterKey = MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()
    private val prefs = EncryptedSharedPreferences.create(context, "binance_secure_keys", masterKey, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV, EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
    fun saveKeys(apiKey: String, secret: String) { prefs.edit().putString("API_KEY", apiKey.trim()).putString("API_SECRET", secret.trim()).putBoolean("HAS_KEYS", apiKey.isNotBlank() && secret.isNotBlank()).apply() }
    fun getApiKey(): String = prefs.getString("API_KEY", "") ?: ""
    fun getSecret(): String = prefs.getString("API_SECRET", "") ?: ""
    fun hasKeys(): Boolean = prefs.getBoolean("HAS_KEYS", false) && getApiKey().isNotBlank() && getSecret().length > 10
    fun clearKeys() { prefs.edit().clear().apply() }
    fun getMode(): String = if(hasKeys()) "REAL TRADE - Binance Ready ✓" else "FREE MODE - gold-api.com (No Key)"
}
