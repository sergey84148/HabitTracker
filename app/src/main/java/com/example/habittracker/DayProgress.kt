package com.example.habittracker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.habittracker.ui.theme.HabitTrackerTheme

@Composable
fun DayProgress(dayWithProgress: DayWithProgress, modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(vertical = 8.dp),
    ) {
        Text(dayWithProgress.name)
        CircularProgressIndicator(progress = { dayWithProgress.progress })
    }
}

@Preview
@Composable
fun DayProgressPreview() {
    HabitTrackerTheme {
        DayProgress(DayWithProgress("Вт", 0.7F))
    }
}

data class DayWithProgress(
    val name: String,
    val progress: Float,
)

@Composable
fun DaysProgressRow(days: List<DayWithProgress>, modifier: Modifier = Modifier) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        days.forEach {
            DayProgress(it)
        }
    }
}

@Preview
@Composable
fun DaysProgressRowPreview() {
    HabitTrackerTheme {
        DaysProgressRow(
            listOf(
                DayWithProgress("Пн", 0.3F),
                DayWithProgress("Вт", 0.1F),
                DayWithProgress("Ср", 0.4F),
                DayWithProgress("Чт", 0.9F),
                DayWithProgress("Пт", 1F),
                DayWithProgress("Сб", 0.25F),
                DayWithProgress("Вс", 0.6F),
            )
        )
    }
}
