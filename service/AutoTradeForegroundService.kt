
package com.snipergold.app.service
import android.app.*
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.snipergold.app.data.GoldApiManager
import kotlinx.coroutines.*
class AutoTradeForegroundService: Service() {
    private val scope = CoroutineScope(Dispatchers.IO)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notif = NotificationCompat.Builder(this, "sniper_channel").setContentTitle("SniperGold TP SMART Active").setContentText("Monitoring Gold 0.01 - FREE gold-api.com").setSmallIcon(android.R.drawable.ic_menu_compass).build()
        startForeground(1, notif)
        scope.launch {
            while(true) {
                val price = GoldApiManager.getLiveGoldPrice()
                // check TP SMART here
                delay(10000)
            }
        }
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? = null
}
