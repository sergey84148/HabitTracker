package com.example.habittracker

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*



@Composable
fun HabitListScreen(viewModel: HabitViewModel) {
    val habits by viewModel.habits.collectAsState()
    LazyColumn {
        items(habits) { habit ->
            HabitCardComposable(habit) {
                viewModel.markAsCompleted(habit.id)
            }
        }
    }
}