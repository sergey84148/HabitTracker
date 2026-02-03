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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import androidx.compose.foundation.layout.defaultMinSize

@Composable
fun HabitListScreen(
    navController: NavHostController,
    viewModel: HabitViewModel
) {
    val habits by viewModel.habits.collectAsState()
    val today = remember { LocalDate.now().dayOfWeek }
    val isLoading by viewModel.isLoading.collectAsState()

    // Список дней недели
    val daysOfWeek = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

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
        Column(modifier = Modifier.fillMaxSize()) {
            // Блок с кругляшами для дней недели
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .defaultMinSize(minHeight = 50.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                daysOfWeek.forEach { day ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .width(50.dp)
                            .padding(4.dp)
                    ) {
                        CircularProgressIndicator(
                            progress = 0f,
                            modifier = Modifier
                                .size(50.dp)
                                .defaultMinSize(minWidth = 50.dp, minHeight = 50.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 8.dp
                        )
                        Text(
                            text = day,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(top = 8.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (habits.isEmpty()) {
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
                if (isLoading) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                progress = 0.3f,
                                strokeWidth = 4.dp,
                                color = Color.Blue
                            )
                        }
                    }
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
}

@Preview(showBackground = true)
@Composable
fun HabitListScreenPreview() {
    val viewModel: HabitViewModel = viewModel()
    val navController = rememberNavController()
    HabitListScreen(navController, viewModel)
}
