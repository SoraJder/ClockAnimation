package com.alina.clockanimation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.alina.clockanimation.presentation.main.MainScreen
import com.alina.clockanimation.presentation.timer.TimerScreen

@Composable
fun ClockAnimationNavHost(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screens.MAIN_SCREEN_ROUTE
    ) {
        composable(
            route = Screens.MAIN_SCREEN_ROUTE
        ) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screens.TIMER_SCREEN_ROUTE
        ) {
            TimerScreen()
        }
    }
}