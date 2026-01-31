package com.example.habittracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import java.time.DayOfWeek
import java.time.LocalDate

private val russianDayNames = arrayOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")

@Composable
fun HabitCardComposable(
    habit: Habit,
    onClick: () -> Unit,
    onDelete: (Int) -> Unit
) {
    val daysState = remember { mutableStateMapOf<DayOfWeek, Boolean>() }
    DayOfWeek.entries.forEach { daysState[it] = false }

    // Определяем текущий день недели
    val today = DayOfWeek.of(LocalDate.now().dayOfWeek.value)

    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { newValue ->
            if (newValue == SwipeToDismissBoxValue.DismissToEnd) {
                onDelete(habit.id)
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        backgroundContent = {
            if (dismissState.currentValue == SwipeToDismissBoxValue.DismissToEnd) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red)
                        .padding(16.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        content = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = onClick
            ) {
                Column(Modifier.padding(4.dp)) {
                    Text(
                        text = habit.name,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow {
                        items(DayOfWeek.entries.size) { index ->
                            val day = DayOfWeek.entries[index]
                            DayOfWeekComposable(
                                day = day,
                                progress = daysState[day] ?: false,
                                isToday = (day == today),
                                onClick = {
                                    if (day == today) {
                                        daysState[day] = !(daysState[day] ?: false)
                                    }
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    )
}

@Composable
fun DayOfWeekComposable(
    day: DayOfWeek,
    progress: Boolean,
    isToday: Boolean,
    onClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(48.dp)
            .padding(4.dp)
            .background(
                if (isToday && progress) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.secondaryContainer,
                CircleShape
            )
            .clickable(enabled = isToday, onClick = onClick)
    ) {
        if (isToday && progress) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        } else {
            Text(
                text = day.shortName(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                color = if (isToday) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

private fun DayOfWeek.shortName(): String {
    return russianDayNames[value - 1]
}
