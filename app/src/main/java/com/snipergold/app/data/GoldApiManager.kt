package com.snipergold.app.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class GoldPrice(
    val price: Double,
    val source: String = "gold-api.com FREE NO KEY",
    val timestamp: Long = System.currentTimeMillis()
)

object GoldApiManager {
    private const val GOLD_URL = "https://api.gold-api.com/price/XAU"

    suspend fun getLiveGoldPrice(): GoldPrice? = withContext(Dispatchers.IO) {
        try {
            val conn = (URL(GOLD_URL).openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 8000
                readTimeout = 8000
                setRequestProperty("Accept", "application/json")
            }
            if (conn.responseCode != 200) return@withContext null
            val str = conn.inputStream.bufferedReader().readText()
            val json = JSONObject(str)
            val price = json.getDouble("price")
            if (price < 500 || price > 15000) return@withContext null
            GoldPrice(price)
        } catch (e: Exception) {
            null
        }
    }
}
