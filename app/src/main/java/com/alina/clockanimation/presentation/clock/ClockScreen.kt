package com.alina.clockanimation.presentation.clock

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alina.clockanimation.R
import com.alina.clockanimation.composables.AnalogClock
import com.alina.clockanimation.composables.ColorDialog
import com.alina.clockanimation.model.hoursMinutesColorList
import com.alina.clockanimation.model.secondColorList
import com.alina.clockanimation.presentation.ui.theme.Typography
import kotlinx.coroutines.delay

@Composable
fun ClockScreen() {
    var time by remember { mutableStateOf(System.currentTimeMillis()) }
    val viewModel = viewModel { ClockViewModel() }

    //STATES OF DIALOG COMPONENTS
    val dialogHourHandState = remember { mutableStateOf(false) }
    val dialogMinuteHandState = remember { mutableStateOf(false) }
    val dialogSecondHandState = remember { mutableStateOf(false) }
    val dialogHourMarkState = remember { mutableStateOf(false) }
    val dialogMinuteMarkState = remember { mutableStateOf(false) }

    //STATE OF COLORS
    val hourHandColor = viewModel.hourHandColor.value
    val minuteHandColor = viewModel.minuteHandColor.value
    val secondHandColor = viewModel.secondHandColor.value
    val hourMarksColor = viewModel.hourMarkColor.value
    val minuteMarksColor = viewModel.minuteMarkColor.value

    LaunchedEffect(true) {
        while (true) {
            time = System.currentTimeMillis()
            delay(1000)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Clock Animation",
            style = Typography.h4,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Hands Colors",
            style = Typography.subtitle1,
            color = MaterialTheme.colors.primary
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            //Hour Hand
            Button(
                onClick = {
                    dialogHourHandState.value = true
                },
                modifier = Modifier.size(88.dp, 40.dp)
            ) {
                Text(text = stringResource(R.string.hour))
            }

            //Minute Hand
            Button(
                onClick = {
                    dialogMinuteHandState.value = true
                },
                modifier = Modifier.size(88.dp, 40.dp)
            ) {
                Text(text = stringResource(R.string.minute))
            }

            //Second Hand
            Button(
                onClick = {
                    dialogSecondHandState.value = true
                },
                modifier = Modifier.size(88.dp, 40.dp)
            ) {
                Text(text = stringResource(R.string.second))
            }

        }
        Text(
            text = "Marks Colors",
            style = Typography.subtitle1,
            color = MaterialTheme.colors.primary
        )

        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement
                .spacedBy(
                    space = 28.dp,
                    alignment = Alignment.CenterHorizontally
                ),
        ) {
            //Hour Mark
            Button(
                onClick = {
                    dialogHourMarkState.value = true
                },
                modifier = Modifier.size(84.dp, 40.dp)
            ) {
                Text(text = stringResource(R.string.hour))
            }

            //Minute Mark
            Button(
                onClick = {
                    dialogMinuteMarkState.value = true
                },
                modifier = Modifier.size(84.dp, 40.dp)
            ) {
                Text(text = stringResource(R.string.minute))
            }

        }

        Box(modifier = Modifier.fillMaxSize(fraction = 0.6f)) {
            AnalogClock(
                hourMarkersColor = hourMarksColor,
                minuteMarkersColor = minuteMarksColor,
                hourHandColor = hourHandColor,
                minuteHandColor = minuteHandColor,
                secondHandColor = secondHandColor,
                timeInMillis = { time }
            )
        }

        when (dialogHourHandState.value) {
            true -> {
                ColorDialog(
                    colors = hoursMinutesColorList,
                    onColorSelected = { color ->
                        viewModel.updateHourHandColor(color)
                        dialogHourHandState.value = false
                    },
                    onDismissRequest = {
                        dialogHourHandState.value = false
                    }
                )
            }

            false -> Unit
        }

        when (dialogMinuteHandState.value) {
            true -> {
                ColorDialog(
                    colors = hoursMinutesColorList,
                    onColorSelected = { color ->
                        viewModel.updateMinuteHandColor(color)
                        dialogMinuteHandState.value = false
                    },
                    onDismissRequest = {
                        dialogMinuteHandState.value = false
                    }
                )
            }

            false -> Unit
        }

        when (dialogSecondHandState.value) {
            true -> {
                ColorDialog(
                    colors = secondColorList,
                    onColorSelected = { color ->
                        viewModel.updateSecondHandColor(color)
                        dialogSecondHandState.value = false

                    },
                    onDismissRequest = {
                        dialogSecondHandState.value = false
                    }
                )
            }

            false -> Unit
        }

        when (dialogHourMarkState.value) {
            true -> {
                ColorDialog(
                    colors = hoursMinutesColorList,
                    onColorSelected = { color ->
                        viewModel.updateHourMarkColor(color)
                        dialogHourMarkState.value = false
                    },
                    onDismissRequest = {
                        dialogHourMarkState.value = false
                    }
                )
            }

            false -> Unit
        }

        when (dialogMinuteMarkState.value) {
            true -> {
                ColorDialog(
                    colors = hoursMinutesColorList,
                    onColorSelected = { color ->
                        viewModel.updateMinuteMarkColor(color)
                        dialogMinuteMarkState.value = false
                    },
                    onDismissRequest = {
                        dialogMinuteMarkState.value = false
                    }
                )
            }

            false -> Unit
        }
    }
}