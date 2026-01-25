package com.example.habittracker

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddHabitScreen(viewModel: HabitViewModel, onSave: () -> Unit) {
    var name by remember { mutableStateOf(TextFieldValue()) }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Text("Новая привычка", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Название привычки") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (name.text.isNotEmpty()) {
                    viewModel.createHabit(name.text)
                    onSave()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddHabitScreenPreview() {
    val viewModel: HabitViewModel = viewModel()
    AddHabitScreen(viewModel, {})
}