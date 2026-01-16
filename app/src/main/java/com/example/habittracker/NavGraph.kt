package com.example.habittracker

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

sealed class Screen(val route: String) {
    object HabitsList : Screen("habits_list")
    object AddHabit : Screen("add_habit")
}

const val ANIMATION_DURATION_MS = 300

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Screen.HabitsList.route) {
        composable(route = Screen.HabitsList.route) {
            HabitsListScreen()
        }
        composable(
            route = Screen.AddHabit.route,
            enterTransition = { slideInVertically(animationSpec = tween(ANIMATION_DURATION_MS)) },
            exitTransition = { slideOutVertically(animationSpec = tween(ANIMATION_DURATION_MS)) }
        ) {
            AddHabitScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}