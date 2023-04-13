package com.alina.clockanimation.model

import androidx.compose.ui.graphics.Color

val hoursMinutesColorMap: Map<String, Color> = mapOf(
    "Default" to Color.Gray,
    "Blue" to Color.Blue,
    "Magenta" to Color.Magenta,
    "Yellow" to Color.Yellow,
    "Red" to Color.Red,
    "Green" to Color.Green
)

val hoursMinutesColorList = hoursMinutesColorMap.keys.toList()

val secondColorMap: Map<String, Color> = mapOf(
    "Default" to Color.Red,
    "Blue" to Color.Blue,
    "Magenta" to Color.Magenta,
    "Yellow" to Color.Yellow,
    "Gray" to Color.Gray,
    "Green" to Color.Green
)
val secondColorList = secondColorMap.keys.toList()