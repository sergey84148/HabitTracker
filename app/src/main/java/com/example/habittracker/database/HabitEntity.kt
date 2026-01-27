package com.example.habittracker.database


import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek


@Entity
data class HabitEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val days: Map<DayOfWeek, Boolean> = mapOf(
        DayOfWeek.MONDAY to false,
        DayOfWeek.TUESDAY to false,
        DayOfWeek.WEDNESDAY to false,
        DayOfWeek.THURSDAY to false,
        DayOfWeek.FRIDAY to false,
        DayOfWeek.SATURDAY to false,
        DayOfWeek.SUNDAY to false
    )
)