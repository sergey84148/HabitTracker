package com.example.habittracker

import java.time.DayOfWeek

data class Habit(
    val id: Int,
    val name: String,
    var isCompleted: Boolean,
    val days: Map<DayOfWeek, Boolean>

)
