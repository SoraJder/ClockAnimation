package com.alina.clockanimation.presentation.timer

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.NotificationCompat
import com.alina.clockanimation.R
import com.alina.clockanimation.presentation.ui.theme.Typography
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit

@Composable
fun TimerScreen() {
    var hours by remember { mutableStateOf(0) }
    var minutes by remember { mutableStateOf(0) }
    var seconds by remember { mutableStateOf(0) }
    var isRunning by remember { mutableStateOf(false) }
    var showResumeButton by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Timer Alarm",
            style = Typography.h4,
            color = MaterialTheme.colors.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            CountDownTextField(
                label = "Hours",
                value = if (isRunning) 0 else hours,
                onValueChange = { hours = it },
                modifier = Modifier.weight(1f),
                editable = !isRunning
            )

            Spacer(modifier = Modifier.width(16.dp))

            CountDownTextField(
                label = "Minutes",
                value = if (isRunning) 0 else minutes,
                onValueChange = { minutes = it },
                modifier = Modifier.weight(1f),
                editable = !isRunning
            )

            Spacer(modifier = Modifier.width(16.dp))

            CountDownTextField(
                label = "Seconds",
                value = if (isRunning) 0 else seconds,
                onValueChange = { seconds = it },
                modifier = Modifier.weight(1f),
                !isRunning
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (isRunning) {
            true -> {
                CountDownDisplay(
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds,
                    timerActive = true,
                    onCountDownFinished = {
                        isRunning = false
                        hours = 0
                        minutes = 0
                        seconds = 0
                        showCountDownFinishedNotification(context)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    isRunning = false
                    showResumeButton = true
                }) {
                    Text("Stop")
                }
            }
            false -> {
                CountDownDisplay(
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds,
                    timerActive = false,
                    onCountDownFinished = {}
                )

                if (showResumeButton) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {

                        Button(onClick = {
                            isRunning = true
                            showResumeButton = false
                        }) {
                            Text("Resume")

                        }

                        Button(onClick = {
                            seconds = 0
                            minutes = 0
                            hours = 0
                            showResumeButton = false
                        }) {
                            Text("New")
                        }
                    }
                } else {
                    Button(onClick = {
                        isRunning = true
                        showResumeButton = false
                    }
                    ) {
                        Text("Start")
                    }
                }
            }
        }
    }
}

@Composable
fun CountDownTextField(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    editable: Boolean
) {
    OutlinedTextField(
        value = if (value == 0) "" else value.toString(),
        onValueChange = { newValue ->
            if (newValue.isEmpty()) {
                onValueChange(0)
            } else {
                val intValue = newValue.toIntOrNull() ?: value
                onValueChange(intValue)
            }
        },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = modifier,
        enabled = editable
    )
}

@Composable
fun CountDownDisplay(
    hours: Int,
    minutes: Int,
    seconds: Int,
    timerActive: Boolean,
    onCountDownFinished: () -> Unit
) {
    var remainingTime by remember { mutableStateOf(getTotalSeconds(hours, minutes, seconds)) }

    LaunchedEffect(Unit) {
        while (remainingTime > 0 && timerActive) {
            remainingTime--
            delay(1000)
        }

        if (remainingTime == 0) {
            onCountDownFinished()
        }
    }

    val timeString = formatTime(remainingTime)

    Text(
        text = timeString,
        fontSize = 64.sp,
        fontWeight = FontWeight.Bold
    )
}

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