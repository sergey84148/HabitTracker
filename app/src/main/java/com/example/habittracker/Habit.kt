package com.example.habittracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habits")
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val completedDays: MutableSet<Int> = mutableSetOf(),
)