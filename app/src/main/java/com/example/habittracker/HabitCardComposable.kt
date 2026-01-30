package com.example.habittracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.DayOfWeek

private val russianDayNames = arrayOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")

@Composable
fun HabitCardComposable(habit: Habit, onClick: () -> Unit) {
    val daysState = remember { mutableStateMapOf<DayOfWeek, Boolean>() }
    DayOfWeek.values().forEach { daysState[it] = false }

    Card(modifier = Modifier.padding(8.dp)) {
        Column(Modifier.padding(4.dp)) {
            Text(
                text = habit.name,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow {
                items(DayOfWeek.entries.size) { index ->
                    val day = DayOfWeek.entries[index]
                    DayOfWeekComposable(day, daysState[day] ?: false) { daysState[day] = !(daysState[day] ?: false) }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun DayOfWeekComposable(day: DayOfWeek, progress: Boolean, onClick: () -> Unit) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier
        .size(48.dp)
        .padding(4.dp)
        .background(MaterialTheme.colorScheme.secondaryContainer, CircleShape)
        .clickable { onClick() }) {
        if (progress) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
        } else {
            Text(text = day.shortName(), textAlign = TextAlign.Center)
        }
    }
}

// Вспомогательная функция для короткого названия дня недели
private fun DayOfWeek.shortName(): String {
    return russianDayNames[value - 1]
}

@Preview(showBackground = true)
@Composable
fun DayOfWeekComposablePreview() {
    val viewModel: HabitViewModel = viewModel()
    AddHabitScreen(viewModel, {})
}