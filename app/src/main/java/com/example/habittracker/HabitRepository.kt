package com.example.habittracker

import kotlinx.coroutines.flow.Flow

interface HabitRepository {
    fun getAll(): Flow<List<Habit>>
    suspend fun insert(habit: Habit)
    suspend fun delete(habit: Habit)
    suspend fun update(habit: Habit)
}

class InMemoryHabitRepository : HabitRepository {
    private val habits = mutableListOf<Habit>()

    override fun getAll(): Flow<List<Habit>> =
        kotlinx.coroutines.flow.flowOf(habits.toList())

    override suspend fun insert(habit: Habit) {
        habits.add(habit)
    }

    override suspend fun delete(habit: Habit) {
        habits.remove(habit)
    }

    override suspend fun update(habit: Habit) {
        val index = habits.indexOfFirst { it.id == habit.id }
        if (index != -1) {
            habits[index] = habit
        }
    }
}