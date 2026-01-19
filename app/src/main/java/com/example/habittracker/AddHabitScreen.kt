package com.example.habittracker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun AddHabitScreen(viewModel: HabitViewModel, onSave: () -> Unit) {
    var name by remember { mutableStateOf("") }
    Column {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название привычки") }
        )
        Button(onClick = {
            if (name.isNotEmpty()) {
                viewModel.createHabit(name)
                onSave()
            }
        }) {
            Text("Сохранить")
        }
    }
}