package com.alina.clockanimation.presentation.timer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {

    private val _timeInSeconds: MutableState<Long> = mutableStateOf(0)
    val timeInSeconds: State<Long> = _timeInSeconds

    private val _timerIsRunning: MutableState<Boolean> = mutableStateOf(false)
    val timerIsRunning: State<Boolean> = _timerIsRunning

    private val _alarmWasSet: MutableState<Boolean> = mutableStateOf(false)
    val alarmWasSet: State<Boolean> = _alarmWasSet

    private val _showSetButton: MutableState<Boolean> = mutableStateOf(true)
    val showSetButton: State<Boolean> = _showSetButton

    private val _showStartButton: MutableState<Boolean> = mutableStateOf(false)
    val showStartButton: State<Boolean> = _showStartButton

    private val _showStopButton: MutableState<Boolean> = mutableStateOf(false)

    private val _showResumeButton: MutableState<Boolean> = mutableStateOf(false)
    val showResumeButton: State<Boolean> = _showResumeButton
    fun updateTimeInSeconds(seconds: Long) {
        _timeInSeconds.value = seconds
    }

    fun updateTimerIsRunning(isRunning: Boolean) {
        _timerIsRunning.value = isRunning
    }

    fun updateAlarmWasSet(alarmWasSet: Boolean) {
        _alarmWasSet.value = alarmWasSet
    }

    fun updateShowSetButton(showSetButton: Boolean) {
        _showSetButton.value = showSetButton
    }

    fun updateShowStartButton(showStartButton: Boolean) {
        _showStartButton.value = showStartButton
    }

    fun updateShowStopButton(showStopButton: Boolean) {
        _showStopButton.value = showStopButton
    }

    fun updateShowResumeButton(showResumeButton: Boolean) {
        _showResumeButton.value = showResumeButton
    }
}