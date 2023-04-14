package com.alina.clockanimation.presentation.main

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.alina.clockanimation.model.hoursMinutesColorMap
import com.alina.clockanimation.model.secondColorMap

//DATA PERSISTENCE
class MainViewModel : ViewModel() {

    private val _hourHandColor: MutableState<Color> =
        mutableStateOf(hoursMinutesColorMap.values.first())
    val hourHandColor: State<Color> = _hourHandColor

    private val _minuteHandColor: MutableState<Color> =
        mutableStateOf(hoursMinutesColorMap.values.first())
    val minuteHandColor: State<Color> = _minuteHandColor

    private val _secondHandColor: MutableState<Color> =
        mutableStateOf(secondColorMap.values.first())
    val secondHandColor: State<Color> = _secondHandColor

    private val _hourMarkColor: MutableState<Color> =
        mutableStateOf(hoursMinutesColorMap.values.first())
    val hourMarkColor: State<Color> = _hourMarkColor

    private val _minuteMarkColor: MutableState<Color> =
        mutableStateOf(hoursMinutesColorMap.values.first())
    val minuteMarkColor: State<Color> = _minuteMarkColor

    fun updateHourHandColor(color: String) {
        _hourHandColor.value = hoursMinutesColorMap.getValue(color)
    }

    fun updateMinuteHandColor(color: String) {
        _minuteHandColor.value = hoursMinutesColorMap.getValue(color)
    }

    fun updateSecondHandColor(color: String) {
        _secondHandColor.value = secondColorMap.getValue(color)
    }

    fun updateHourMarkColor(color: String) {
        _hourMarkColor.value = hoursMinutesColorMap.getValue(color)
    }

    fun updateMinuteMarkColor(color: String) {
        _minuteMarkColor.value = hoursMinutesColorMap.getValue(color)
    }
}