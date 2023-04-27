package com.alina.clockanimation

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class ClockAnimationApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "timer_alarm",
            "Timer Alarm",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.description = "Used for timer alarm notifications"

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}