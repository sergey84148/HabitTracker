package com.example.habittracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(private val repository: HabitRepository) : ViewModel() {

    private val _state = MutableStateFlow(listOf<Habit>())
    val state: StateFlow<List<Habit>> = _state

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getAll().collect { list ->
                _state.value = list
            }
        }
    }

    fun addHabit(habit: Habit) {
        viewModelScope.launch {
            repository.insert(habit)
        }
    }

    fun removeHabit(habit: Habit) {
        viewModelScope.launch {
            repository.delete(habit)
        }
    }

    fun toggleCompletion(habit: Habit, dayIndex: Int) {
        viewModelScope.launch {
            val updatedHabit = habit.copy(completedDays = habit.completedDays.apply {
                if (contains(dayIndex)) remove(dayIndex) else add(dayIndex)
            })
            repository.update(updatedHabit)
        }
    }
}