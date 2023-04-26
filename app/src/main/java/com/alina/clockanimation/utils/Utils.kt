package com.alina.clockanimation.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.alina.clockanimation.R
import java.util.concurrent.TimeUnit

object Utils {

    fun getTotalSeconds(hours: Int, minutes: Int, seconds: Int): Int {
        return hours * 3600 + minutes * 60 + seconds
    }

    fun formatTime(totalSeconds: Int): String {
        val hours = TimeUnit.SECONDS.toHours(totalSeconds.toLong())
        val minutes = TimeUnit.SECONDS.toMinutes(totalSeconds.toLong() - hours * 3600)
        val seconds = TimeUnit.SECONDS.toSeconds(totalSeconds.toLong() - hours * 3600 - minutes * 60)
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    //TODO de rezolvat ca nu e bine ce e pe aici
    fun showCountDownFinishedNotification(context: Context) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "countdown_timer"
        val channel = NotificationChannel(
            channelId,
            "Countdown Timer",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(channel)
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Countdown Timer Finished")
            .setContentText("Your countdown timer has finished!")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()
        notificationManager.notify(1, notification)
    }

}