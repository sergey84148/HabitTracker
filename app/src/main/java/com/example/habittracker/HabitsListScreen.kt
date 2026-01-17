package com.example.habittracker

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import kotlinx.coroutines.launch

@Composable
fun HabitsListScreen(viewModel: HabitViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val habits = viewModel.state.collectAsState().value

    /*Scaffold(topBar = {
        SmallTopAppBar(title = { Text("Трекер привычек") })
    }) {
        Surface(modifier = Modifier.fillMaxSize()) {
            BoxWithConstraints {
                if (habits.isNotEmpty()) {
                    LazyColumn {
                        itemsIndexed(habits) { _, habit ->
                            HabitItem(habit = habit, onDelete = {
                                scope.launch {
                                    viewModel.removeHabit(habit)
                                    Toast.makeText(context, "Удалено", Toast.LENGTH_SHORT).show()
                                }
                            })
                        }
                    }
                } else {
                    Text("Нет привычек.", modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }*/
}

@Composable
private fun HabitItem(habit: Habit, onDelete: () -> Unit) {
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
            .fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(habit.title, style = MaterialTheme.typography.bodyLarge)
                Text("${habit.completedDays.size}/7", style = MaterialTheme.typography.labelMedium)
            }
            IconButton(onClick = onDelete) {
                Icon(imageVector = Icons.Filled.Delete, contentDescription = "Удалить")
            }
        }
    }
}