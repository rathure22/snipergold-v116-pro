
package com.snipergold.app.data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
data class GoldPrice(val price: Double, val source: String = "gold-api.com FREE NO KEY", val timestamp: Long = System.currentTimeMillis())
object GoldApiManager {
    private const val GOLD_URL = "https://api.gold-api.com/price/XAU"
    suspend fun getLiveGoldPrice(): GoldPrice? = withContext(Dispatchers.IO) {
        try {
            val str = URL(GOLD_URL).readText()
            val json = JSONObject(str)
            val price = json.getDouble("price")
            if(price < 500 || price > 15000) return@withContext null
            GoldPrice(price)
        } catch(e:Exception){ null }
    }
}
