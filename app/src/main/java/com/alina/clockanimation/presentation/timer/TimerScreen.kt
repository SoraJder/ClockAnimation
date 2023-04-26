package com.alina.clockanimation.presentation.timer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alina.clockanimation.presentation.ui.theme.Typography
import com.alina.clockanimation.utils.Utils
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.duration.DurationDialog
import com.maxkeppeler.sheets.duration.models.DurationConfig
import com.maxkeppeler.sheets.duration.models.DurationFormat
import com.maxkeppeler.sheets.duration.models.DurationSelection
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerScreen() {
    val viewModel = viewModel { TimerViewModel() }
    val second = viewModel.second
    val minute = viewModel.minute
    val hour = viewModel.hour

    val context = LocalContext.current

    val selectedTimeInSeconds = remember { mutableStateOf<Long>(0) }
    val selectedTime = rememberUseCaseState()

    DurationDialog(
        state = selectedTime,
        selection = DurationSelection { newTimeInSeconds ->
            selectedTimeInSeconds.value = newTimeInSeconds
            selectedTime.hide()
        },
        config = DurationConfig(
            timeFormat = DurationFormat.HH_MM_SS,
            currentTime = selectedTimeInSeconds.value,
        )
    )

    var showTime = Utils.formatTime(selectedTimeInSeconds.value)

    var timerIsRunning by rememberSaveable { mutableStateOf(false) }
    var alarmWasSet by rememberSaveable { mutableStateOf(false) }

    var showSetButton by rememberSaveable { mutableStateOf(true) }
    var showStartButton by rememberSaveable { mutableStateOf(false) }
    var showStopButton by rememberSaveable { mutableStateOf(false) }
    var showResumeButton by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Timer Alarm",
            style = Typography.h4,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = showTime,
            style = Typography.h3,
            color = MaterialTheme.colors.primary,
            modifier = Modifier
                .padding(16.dp)
        )

        if (!alarmWasSet && showSetButton) {
            Button(
                onClick = {
                    selectedTime.show()
                    alarmWasSet = true
                    showSetButton = false
                    showStartButton = true
                    showResumeButton = false
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Set Alarm")
            }
        }

        if (showStartButton) {
            Button(
                onClick = {
                    showStartButton = false
                    showStopButton = true
                    timerIsRunning = true
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Start Alarm")
            }
        }

        if (timerIsRunning) {
            LaunchedEffect(Unit) {
                while (selectedTimeInSeconds.value > 0) {
                    selectedTimeInSeconds.value--
                    delay(1000)
                }

                if (selectedTimeInSeconds.value.toInt() == 0) {
                    showSetButton = true
                    alarmWasSet = false
                    timerIsRunning = false
                    Utils.showCountDownFinishedNotification(context)
                }
            }

            Button(
                onClick = {
                    timerIsRunning = false
                    showResumeButton = true
                    showSetButton = true
                    alarmWasSet = false
                    showStopButton = false
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Stop Alarm")
            }
        }

        if (showResumeButton) {
            Button(
                onClick = {
                    timerIsRunning = true
                    showStopButton = true
                    alarmWasSet = true
                    showResumeButton = false
                }
            ) {
                Text(text = "Resume Alarm")
            }
        }
    }
}