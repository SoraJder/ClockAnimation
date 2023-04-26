package com.alina.clockanimation.presentation.timer

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alina.clockanimation.presentation.ui.theme.Typography

@Composable
fun TimerScreen() {
    val viewModel = viewModel { TimerViewModel() }

    var hours = viewModel.hour.value
    var minutes = viewModel.minute.value
    var seconds = viewModel.second.value

    var isRunning by remember { mutableStateOf(false) }
    var startButtonWasPressed by remember { mutableStateOf(false) }
    var timerActive by remember { mutableStateOf(false) }

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
                value = hours,
                onValueChange = { viewModel.updateHour(it) },
                modifier = Modifier.weight(1f),
                editable = !isRunning
            )

            Spacer(modifier = Modifier.width(16.dp))

            CountDownTextField(
                label = "Minutes",
                value = minutes,
                onValueChange = { viewModel.updateMinute(it) },
                modifier = Modifier.weight(1f),
                editable = !isRunning
            )

            Spacer(modifier = Modifier.width(16.dp))

            CountDownTextField(
                label = "Seconds",
                value = seconds,
                onValueChange = { viewModel.updateSecond(it) },
                modifier = Modifier.weight(1f),
                editable = !isRunning
            )
        }

        when (isRunning) {

            true -> {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Stop")
                }
            }

            false -> {

                when (startButtonWasPressed) {

                    true -> {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Resume")
                        }
                        Button(onClick = {
                            /*TODO*/
                            startButtonWasPressed = false
                        }) {
                            Text(text = "New")
                        }
                    }

                    false -> {
                        Button(onClick = { /*TODO*/
                            timerActive = true
                            startButtonWasPressed = true
                        }) {
                            Text(text = "Start")
                        }
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