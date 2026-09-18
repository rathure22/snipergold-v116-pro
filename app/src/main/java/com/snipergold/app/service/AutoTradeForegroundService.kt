package com.snipergold.app.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.snipergold.app.data.GoldApiManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AutoTradeForegroundService : Service() {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notif = NotificationCompat.Builder(this, "sniper_channel")
            .setContentTitle("SniperGold TP SMART Active")
            .setContentText("Monitoring Gold 0.01 - FREE gold-api.com")
            .setSmallIcon(android.R.drawable.ic_menu_compass)
            .setOngoing(true)
            .build()
        startForeground(1, notif)

        scope.launch {
            while (true) {
                try {
                    GoldApiManager.getLiveGoldPrice()
                    // TP SMART checks can be hooked here if desired
                } catch (_: Exception) {
                }
                delay(10_000)
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
