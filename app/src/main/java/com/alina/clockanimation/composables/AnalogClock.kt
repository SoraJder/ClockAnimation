package com.alina.clockanimation.composables

import android.graphics.Paint
import android.graphics.Rect
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import java.util.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

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

    val color = colors.background

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

        val angleDegreeDifference = (360f / 60f)
        (1..60).forEach {
            val angleRadDifference =
                (((angleDegreeDifference * it) - 90f) * (PI / 180f)).toFloat()
            val lineLength = if (it % 5 == 0) circleRadius * .85f else circleRadius * .93f
            val lineColour = if (it % 5 == 0)  hourMarkersColor else minuteMarkersColor
            val lineWidth = if(it%5==0) 6f else 4f
            val startOffsetLine = Offset(
                x = lineLength * cos(angleRadDifference) + size.center.x,
                y = lineLength * sin(angleRadDifference) + size.center.y
            )
            val endOffsetLine = Offset(
                x = (circleRadius - ((circleRadius * .05f) / 2) ) * cos(angleRadDifference) + size.center.x,
                y = (circleRadius - ((circleRadius * .05f) / 2) ) * sin(angleRadDifference) + size.center.y
            )
            drawLine(
                color = lineColour,
                start = startOffsetLine,
                end = endOffsetLine,
                strokeWidth = lineWidth
            )
            if (it % 5 == 0) {
                //here we are using the native canvas (native canvas is the traditional one we use dto work with the views), so that we can draw text on the canvas
                drawContext.canvas.nativeCanvas.apply {
                    val positionX = (circleRadius * .75f) * cos(angleRadDifference) + size.center.x
                    val positionY = (circleRadius * .75f) * sin(angleRadDifference) + size.center.y
                    val text = (it / 5).toString()
                    val paint = Paint()
                    paint.textSize = circleRadius * .15f
                    paint.color = color.toArgb()
                    val textRect = Rect()
                    paint.getTextBounds(text, 0, text.length, textRect)

                    drawText(
                        text,
                        positionX - (textRect.width() / 2),
                        positionY + (textRect.width() / 2),
                        paint
                    )
                }
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