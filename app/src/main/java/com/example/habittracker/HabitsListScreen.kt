package com.example.habittracker

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate



@Composable
fun HabitListScreen(
    navController: NavHostController,
    viewModel: HabitViewModel
) {
    val habits by viewModel.habits.collectAsState()
    val today = remember { LocalDate.now().dayOfWeek }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_habit") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Введите название привычки"
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {if (habits.isEmpty()) {
            item {
                Text(
                    text = "Нажмите + для добавления привычки или смахните влево для удаления",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(unbounded = false)
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
            item {
                Text(
                    text = "Мои привычки",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(unbounded = false)
                        .padding(vertical = 16.dp),
                    textAlign = TextAlign.Center
                )
            }

            items(habits, key = { it.id }) { habit ->
                HabitCardComposable(
                    today = today,
                    name = habit.name,
                    daysProgress = habit.days,
                    onClick = { viewModel.markAsCompleted(habit.id) },
                    onDelete = {
                        viewModel.removeHabit(habit.id)
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HabitListScreenPreview() {
    val viewModel: HabitViewModel = viewModel()
    val navController = rememberNavController()
    HabitListScreen(navController, viewModel)
}
