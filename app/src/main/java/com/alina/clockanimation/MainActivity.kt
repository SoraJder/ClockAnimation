package com.alina.clockanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.alina.clockanimation.presentation.MainScreen
import com.alina.clockanimation.presentation.ui.theme.ClockAnimationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockAnimationTheme {
                setContent {
                    MainScreen()
                }
            }
        }
    }
}