package com.pdmtaller2.laboratorio5.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.pdmtaller2.laboratorio5.navigation.ScreenRoutes

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(modifier = Modifier.padding(8.dp), onClick = { navController.navigate(ScreenRoutes.todo) }) {
            Text("Todo")
        }
        Button(modifier = Modifier.padding(8.dp), onClick = { navController.navigate(ScreenRoutes.sensor) }) {
            Text("Sensores")
        }
    }
}