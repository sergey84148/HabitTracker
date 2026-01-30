package com.example.habittracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.database.HabitEntity
import com.example.habittracker.database.HabitDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import javax.inject.Inject

@HiltViewModel
class HabitViewModel @Inject constructor(
    private val dao: HabitDao
) : ViewModel() {

    // Преобразуем HabitEntity → Habit для UI
    val habits = dao.observeAllHabits()
        .map { entities ->
            entities.map { entity ->
                Habit(
                    id = entity.id,
                    name = entity.name,
                    days = entity.days
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun createHabit(name: String) {
        val newHabit = HabitEntity(
            name = name,
            days = mapOf(
                DayOfWeek.MONDAY to false,
                DayOfWeek.TUESDAY to false,
                DayOfWeek.WEDNESDAY to false,
                DayOfWeek.THURSDAY to false,
                DayOfWeek.FRIDAY to false,
                DayOfWeek.SATURDAY to false,
                DayOfWeek.SUNDAY to false
            )
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(newHabit)
        }
    }

    fun markAsCompleted(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val habit = dao.getHabitById(id)
            habit?.let {
                val updatedHabit = it.copy(
                    days = it.days.toMutableMap().apply {
                        forEach { entry -> this[entry.key] = true }
                    }
                )
                dao.update(updatedHabit)
            }
        }
    }
}
