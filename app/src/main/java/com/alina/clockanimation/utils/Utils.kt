package com.alina.clockanimation.utils

import java.util.concurrent.TimeUnit

object Utils {

    fun formatTime(totalSeconds: Long): String {
        val hours = TimeUnit.SECONDS.toHours(totalSeconds)
        val minutes = TimeUnit.SECONDS.toMinutes(totalSeconds - hours * 3600)
        val seconds = TimeUnit.SECONDS.toSeconds(totalSeconds - hours * 3600 - minutes * 60)
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}