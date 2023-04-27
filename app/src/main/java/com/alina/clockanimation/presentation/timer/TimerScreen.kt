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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alina.clockanimation.R
import com.alina.clockanimation.presentation.ui.theme.Typography
import com.alina.clockanimation.utils.TimerAlarmNotificationService
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
    val viewModel: TimerViewModel = viewModel(modelClass = TimerViewModel::class.java)

    val context = LocalContext.current

    var selectedTimeInSeconds: Long = viewModel.timeInSeconds.value

    val timerIsRunning: Boolean = viewModel.timerIsRunning.value
    val alarmWasSet: Boolean = viewModel.alarmWasSet.value

    val showSetButton: Boolean = viewModel.showSetButton.value
    val showStartButton: Boolean = viewModel.showStartButton.value
    val showResumeButton: Boolean = viewModel.showResumeButton.value

    val selectedTime = rememberUseCaseState()

    DurationDialog(
        state = selectedTime,
        selection = DurationSelection { newTimeInSeconds ->
            viewModel.updateTimeInSeconds(newTimeInSeconds)
            selectedTime.hide()
        },
        config = DurationConfig(
            timeFormat = DurationFormat.HH_MM_SS,
            currentTime = selectedTimeInSeconds,
        )
    )

    val showTime = Utils.formatTime(selectedTimeInSeconds)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.timer_alarm),
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
                    viewModel.updateAlarmWasSet(true)
                    viewModel.updateShowSetButton(false)
                    viewModel.updateShowStartButton(true)
                    viewModel.updateShowResumeButton(false)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.set_alarm),
                    color = Color.White
                )
            }
        }

        if (showStartButton) {
            Button(
                onClick = {
                    viewModel.updateShowStartButton(false)
                    viewModel.updateShowStopButton(true)
                    viewModel.updateTimerIsRunning(true)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.start_alarm),
                    color = Color.White
                )
            }
        }


        if (timerIsRunning) {
            LaunchedEffect(Unit) {
                while (selectedTimeInSeconds > 0) {
                    selectedTimeInSeconds--
                    viewModel.updateTimeInSeconds(selectedTimeInSeconds)
                    delay(1000)
                }

                if (selectedTimeInSeconds.toInt() == 0) {
                    viewModel.updateShowSetButton(true)
                    viewModel.updateAlarmWasSet(false)
                    viewModel.updateTimerIsRunning(false)

                    TimerAlarmNotificationService(context).showNotification()
                }
            }


            Button(
                onClick = {
                    viewModel.updateTimerIsRunning(false)
                    viewModel.updateShowResumeButton(true)
                    viewModel.updateShowSetButton(true)
                    viewModel.updateAlarmWasSet(false)
                    viewModel.updateShowStopButton(false)
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.stop_alarm),
                    color = Color.White
                )
            }
        }

        if (showResumeButton) {
            Button(
                onClick = {
                    viewModel.updateTimerIsRunning(true)
                    viewModel.updateShowStopButton(true)
                    viewModel.updateAlarmWasSet(true)
                    viewModel.updateShowResumeButton(false)
                }
            ) {
                Text(
                    text = stringResource(R.string.resume_alarm),
                    color = Color.White
                )
            }
        }
    }
}
