package com.example.habittracker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AddHabitScreen(viewModel: HabitViewModel = hiltViewModel(), onNavigateBack: () -> Unit) {
    var title by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            SmallTopAppBar(title = { Text(stringResource(R.string.create_habit_title)) })
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.enter_habit_name)) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (title.isNotBlank()) {
                    viewModel.addHabit(Habit(title = title))
                    onNavigateBack()
                }
            }) {
                Text(stringResource(R.string.save_button_label))
            }
        }
    }
}