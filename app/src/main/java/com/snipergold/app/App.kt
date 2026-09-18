package com.snipergold.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "sniper_channel",
                "SniperGold Auto Trade",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Gold price monitoring & TP SMART"
            }
            val nm = getSystemService(NotificationManager::class.java)
            nm.createNotificationChannel(channel)
        }
    }
}
