package com.example.habittracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HabitViewModel : ViewModel() {
    private val _habits = MutableStateFlow(emptyList<Habit>())
    val habits: StateFlow<List<Habit>> = _habits

    fun createHabit(name: String) {
        val newHabit = Habit(
            id = habits.value.size + 1,
            name = name,
            days = mutableMapOf()
        )
        _habits.value = habits.value + newHabit
    }

    fun markAsCompleted(id: Int) {
        val updatedHabits = habits.value.map { habit ->
            if (habit.id == id) {
                habit.copy(days = habit.days.mapValues { it.value || true }.toMutableMap())
            } else {
                habit
            }
        }
        _habits.value = updatedHabits
    }


}