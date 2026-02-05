package com.example.habittracker

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import androidx.compose.ui.tooling.preview.Preview

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
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Удалить дело",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        content = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                //onClick = onClick  //здесь убрал
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
                                onClick = onClick,// Здесь добавил
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
        DayOfWeek.MONDAY to false,
        DayOfWeek.TUESDAY to false,
        DayOfWeek.WEDNESDAY to false,
        DayOfWeek.THURSDAY to false,
        DayOfWeek.FRIDAY to false,
        DayOfWeek.SATURDAY to false,
        DayOfWeek.SUNDAY to false
    )
    HabitCardComposable(
        name = "Android",
        daysProgress = testProgress,
        today = DayOfWeek.WEDNESDAY,
        onClick = {},
        onDelete = {}
    )
}
