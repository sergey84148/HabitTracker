package com.example.habittracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import java.time.DayOfWeek
@Composable
fun HabitCardComposable(habit: Habit, onClick: () -> Unit) {
    Card {
        Column {
            Text(habit.name)
            Row {
                for (day in DayOfWeek.values()) {
                    DayOfWeekComposable(day, habit.days[day] ?: false)
                }
            }
            Button(onClick = onClick) {
                Text("Mark as completed")
            }
        }
    }
}

@Composable
fun DayOfWeekComposable(day: DayOfWeek, progress: Boolean) {
    Row {
        Text(day.name)
        if (progress) {
            Icon(Icons.Default.Check, contentDescription = "Completed")
        }
    }
}