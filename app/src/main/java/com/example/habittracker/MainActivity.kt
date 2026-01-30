package com.example.habittracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.habittracker.ui.theme.HabitTrackerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: HabitViewModel = viewModel()
            val navController = rememberNavController()
            HabitTrackerTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
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
            }
        }
    }
}

