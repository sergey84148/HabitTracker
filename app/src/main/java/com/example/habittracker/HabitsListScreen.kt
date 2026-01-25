package com.example.habittracker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

// Основной экран с списком привычек
@Composable
fun HabitListScreen(navController: NavHostController, viewModel: HabitViewModel) {
    val habits by viewModel.habits.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_habit") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Habit")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Заголовок страницы
            item {
                Text(
                    text = "Привычки",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(unbounded = false)
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
/*
            // Список привычек
            items(habits) { habit ->
                HabitCardComposable(habit) {
                    viewModel.markAsCompleted(habit.id)
                }
            }*/
        }
    }
}

// Предварительный просмотр экрана списка привычек
@Preview(showBackground = true)
@Composable
fun HabitListScreenPreview() {
    val viewModel: HabitViewModel = viewModel()
    val navController = rememberNavController()
    HabitListScreen(navController, viewModel)
}