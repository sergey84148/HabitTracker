package com.example.habittracker



class HabitRepository {
    private val habits = mutableListOf<Habit>()

    fun getHabits(): List<Habit> {
        return habits
    }

    fun addHabit(habit: Habit) {
        habits.add(habit)
    }

    fun removeHabit(habit: Habit) {
        habits.remove(habit)
    }
}