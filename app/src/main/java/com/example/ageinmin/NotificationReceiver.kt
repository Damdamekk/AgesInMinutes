package com.example.ageinmin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notification = NotificationCompat.Builder(context, "daily_notification_channel")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Zmień na istniejącą ikonę
            .setContentTitle("Daily Reminder")
            .setContentText("Don't forget to check your age in minutes!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, notification)
    }
}