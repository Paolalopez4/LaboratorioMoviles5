package com.pdmtaller2.laboratorio5.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pdmtaller2.laboratorio5.ui.home.HomeScreen
import com.pdmtaller2.laboratorio5.ui.sensor.SensorScreen
import com.pdmtaller2.laboratorio5.ui.todo.TodoScreen
import com.pdmtaller2.laboratorio5.viewmodel.TodoViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.home
    ) {
        composable(ScreenRoutes.home) {
            HomeScreen(navController)
        }
        composable(ScreenRoutes.todo) {
            val todoViewModel = viewModel<TodoViewModel>()
            TodoScreen(navController, todoViewModel)
        }
        composable(ScreenRoutes.sensor) {
            SensorScreen(navController)
        }
    }
}