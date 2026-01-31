package com.example.habittracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.filled.Check
import androidx.compose.material3.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.ripple.rememberRipple

private val russianDayNames = arrayOf("пн", "вт", "ср", "чт", "пт", "сб", "вс")

@Composable
fun HabitCardComposable(
    name: String,
    daysProgress: Map<DayOfWeek, Boolean>,
    today: DayOfWeek,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { newValue ->
            if (newValue == SwipeToDismissBoxValue.DismissToEnd) {
                onDelete()
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
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Удалить привычку",
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
                        text = name,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow {
                        items(DayOfWeek.entries.size) { index ->
                            val day = DayOfWeek.entries[index]
                            DayOfWeekComposable(
                                day = day,
                                progress = daysProgress[day] ?: false,
                                isToday = (day == today),
                                onClick = {} // заглушка для Preview
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
            .clickable(
                enabled = isToday,
                onClick = onClick,
                indication = rememberRipple()
            )
    ) {
        if (isToday && progress) {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        } else {
            Text(
                text = day.shortName(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall,
                color = if (isToday) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

private fun DayOfWeek.shortName(): String {
    return russianDayNames[value - 1]
}

@Preview(
    name = "Default HabitCard",
    group = "HabitCards",
    showBackground = true,
    widthDp = 320,
    heightDp = 180
)
@Composable
fun HabitCardComposablePreview() {
    val testProgress = mapOf(
        DayOfWeek.MONDAY to true,
        DayOfWeek.TUESDAY to false,
        DayOfWeek.WEDNESDAY to true,
        DayOfWeek.THURSDAY to false,
        DayOfWeek.FRIDAY to true,
        DayOfWeek.SATURDAY to false,
        DayOfWeek.SUNDAY to true
    )
    HabitCardComposable(
        name = "Android",
        daysProgress = testProgress,
        today = DayOfWeek.WEDNESDAY,
        onClick = {},
        onDelete = {}
    )
}
