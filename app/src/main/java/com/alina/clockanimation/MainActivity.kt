package com.alina.clockanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.alina.clockanimation.navigation.ClockAnimationNavHost
import com.alina.clockanimation.presentation.ui.theme.ClockAnimationTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ClockAnimationTheme {
                val navController = rememberNavController()
                ClockAnimationNavHost(navController = navController)
            }
        }
    }
}