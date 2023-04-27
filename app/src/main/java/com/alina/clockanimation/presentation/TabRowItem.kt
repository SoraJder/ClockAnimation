package com.alina.clockanimation.presentation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.alina.clockanimation.presentation.clock.ClockScreen
import com.alina.clockanimation.presentation.timer.TimerScreen

sealed class TabRowItem(
    val title: String,
    val icon: ImageVector,
    val screen: @Composable () -> Unit
) {

    object AnalogClock : TabRowItem(
        title = "Analog Clock",
        icon = Icons.Default.Home,
        screen = { ClockScreen() }
    )

    object TimerAlarm : TabRowItem(
        title = "Timer Alarm",
        icon = Icons.Default.Star,
        screen = { TimerScreen() }
    )
}