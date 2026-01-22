package com.example.habittracker

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val viewModel: HabitViewModel = viewModel()
    NavHost(navController, startDestination = "habit_list") {
        composable("habit_list") {
            HabitListScreen(navController, viewModel)
        }
        composable("add_habit") {
            AddHabitScreen(viewModel) {
                navController.navigate("habit_list")
            }
        }
    }
}