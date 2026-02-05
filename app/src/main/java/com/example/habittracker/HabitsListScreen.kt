package com.example.habittracker

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import java.time.DayOfWeek
import java.time.LocalDate

private val daysOfWeekNames = mapOf(
    DayOfWeek.MONDAY to "Пн",
    DayOfWeek.TUESDAY to "Вт",
    DayOfWeek.WEDNESDAY to "Ср",
    DayOfWeek.THURSDAY to "Чт",
    DayOfWeek.FRIDAY to "Пт",
    DayOfWeek.SATURDAY to "Сб",
    DayOfWeek.SUNDAY to "Вс",
)

@Composable
fun HabitListScreen(
    navController: NavHostController,
    viewModel: HabitViewModel
) {
    val habits by viewModel.habits.collectAsState()
    val today = remember { LocalDate.now().dayOfWeek }
    val isLoading by viewModel.isLoading.collectAsState()

    // Список дней недели
    val daysWithProgress = remember(habits) {
        DayOfWeek.entries.map { dayOfWeek ->
            val doneHabits = habits.sumOf { habit ->
                habit.days.count { it.key == dayOfWeek && it.value }
            }

            val progress = doneHabits.toFloat() / habits.size.toFloat()

            DayWithProgress(daysOfWeekNames.getValue(dayOfWeek), progress)
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_habit") },
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Введите название дела"
                )
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize()) {
            // Блок с кругляшами для дней недели
            DaysProgressRow(daysWithProgress)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (habits.isEmpty()) {
                    item {
                        Text(
                            text = "Нажмите + \nдля добавления дела  \nа при удалении добавленного смахните влево.\nЧто бы отметить как выполнено нужно нажать на день недели на созданном деле. ",
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
                            // На самом деле, не уверен, что при 100% работе с БД есть в этом необходимость и будет как-то заметно)
                            CircularProgressIndicator(
                                strokeWidth = 4.dp, // Если нужна просто крутилка, прогресс не указываем
                                color = Color.Blue,
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
