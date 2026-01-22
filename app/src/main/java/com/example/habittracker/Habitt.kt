package com.example.habittracker

import java.time.DayOfWeek

data class Habit(
    val id: Int,
    val name: String,
    val days: MutableMap<DayOfWeek, Boolean>
)