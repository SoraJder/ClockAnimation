package com.alina.clockanimation.presentation.timer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class TimerViewModel : ViewModel() {
    private val _hour: MutableState<Int> = mutableStateOf(0)
    val hour: State<Int> = _hour

    private val _minute: MutableState<Int> = mutableStateOf(0)
    val minute: State<Int> = _minute

    private val _second: MutableState<Int> = mutableStateOf(0)
    val second: State<Int> = _second

    fun updateHour(hour: Int) {
        _hour.value = hour
    }

    fun updateMinute(minute: Int) {
        _minute.value = minute
    }

    fun updateSecond(second: Int) {
        _second.value = second
    }
}