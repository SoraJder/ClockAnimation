package com.alina.clockanimation.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.sp
import java.util.*

@OptIn(ExperimentalTextApi::class)
@Composable
fun AnalogClock(
    backgroundColor: Color = colors.onBackground,
    thicknessColor: Color = colors.onBackground,
    hourMarkersColor: Color = Color.Gray,
    minuteMarkersColor: Color = Color.Gray,
    hourHandColor: Color = Color.Gray,
    minuteHandColor: Color = Color.Gray,
    secondHandColor: Color = Color.Red,
    timeInMillis: () -> Long,
) {

    val textMeasurer = rememberTextMeasurer()
    val color = colors.onBackground

    val date = Date(timeInMillis.invoke())
    val calendar = Calendar.getInstance().apply {
        time = date
    }

    val second = calendar.get(Calendar.SECOND)
    val minute = calendar.get(Calendar.MINUTE)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)

    Canvas(modifier = Modifier.fillMaxSize()) {

        val circleRadius = (size.width / 2f)
        val canvasCenter = Offset(x = size.width / 2f, y = size.height / 2f)

        //background
        drawCircle(
            color = backgroundColor,
            radius = circleRadius,
            center = canvasCenter
        )

        //clock thickness
        drawCircle(
            color = thicknessColor,
            style = Stroke(
                width = 16f
            ),
            center = canvasCenter
        )

        val minuteMarkerLength = circleRadius / 15f
        repeat(60) {
            rotate((it / 60f) * 360) {
                val start = center - Offset(0f, circleRadius)
                val end = start + Offset(0f, minuteMarkerLength)
                drawLine(
                    color = minuteMarkersColor,
                    start = start,
                    end = end,
                    strokeWidth = 6f
                )
            }
        }

        val hourMarkerLength = circleRadius / 10f
        repeat(12) {
            rotate((it / 12f) * 360) {
                val start = center - Offset(0f, circleRadius)
                val end = start + Offset(0f, hourMarkerLength)
                drawLine(
                    color = hourMarkersColor,
                    start = start,
                    end = end,
                    strokeWidth = 10f
                )
                drawText(
                    textMeasurer = textMeasurer,
                    text = it.toString(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = color,
                    ),
                    topLeft = Offset.Zero
                )
            }
        }

        val secondRatio = second / 60f
        val minuteRatio = (minute + secondRatio) / 60f
        val hourRatio = (hour + minuteRatio) / 12f

        rotate(hourRatio * 360) {
            drawLine(
                color = hourHandColor,
                start = center - Offset(0f, circleRadius * 0.6f),
                end = center,
                strokeWidth = 12f
            )
        }

        rotate(minuteRatio * 360) {
            drawLine(
                color = minuteHandColor,
                start = center - Offset(0f, circleRadius * 0.8f),
                end = center,
                strokeWidth = 8f
            )
        }

        rotate(secondRatio * 360) {
            drawLine(
                color = secondHandColor,
                start = center - Offset(0f, circleRadius * 0.9f),
                end = center,
                strokeWidth = 4f
            )
        }
    }
}