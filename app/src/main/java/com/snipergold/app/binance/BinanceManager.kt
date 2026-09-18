package com.snipergold.app.binance

import java.net.HttpURLConnection
import java.net.URL
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Lightweight Binance REST helper.
 * Real order placement is intentionally minimal / stubbed for safety.
 * checkConnection() does a signed account call when keys exist.
 */
class BinanceManager(private val storage: SecureApiStorage) {

    fun hmacSHA256(data: String, secret: String): String {
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(SecretKeySpec(secret.toByteArray(Charsets.UTF_8), "HmacSHA256"))
        return mac.doFinal(data.toByteArray(Charsets.UTF_8))
            .joinToString("") { "%02x".format(it) }
    }

    fun checkConnection(): Boolean {
        if (!storage.hasKeys()) return false
        return try {
            val ts = System.currentTimeMillis()
            val query = "timestamp=$ts"
            val sig = hmacSHA256(query, storage.getSecret())
            val url = URL("https://api.binance.com/api/v3/account?$query&signature=$sig")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                setRequestProperty("X-MBX-APIKEY", storage.getApiKey())
                connectTimeout = 8000
                readTimeout = 8000
            }
            conn.responseCode == 200
        } catch (e: Exception) {
            false
        }
    }

    fun getBalanceParsed(): Double {
        // Placeholder – parse free USDT from /api/v3/account if needed later
        return 0.0
    }

    /**
     * Place order stub. Returns a local order id string for history.
     * Real POST /api/v3/order can be filled when you want live trading.
     */
    fun placeRealOrder(
        symbol: String,
        side: String,
        qty: Double,
        sl: Double,
        tp: Double
    ): String? {
        if (!storage.hasKeys()) return null
        // Intentionally not firing real market order here for safety.
        // Uncomment & complete signed POST when ready for live.
        return "ORDER_${System.currentTimeMillis()}"
    }
}
