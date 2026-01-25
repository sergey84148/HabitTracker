package com.example.habittracker.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.habittracker.database.HabitEntity
import com.example.habittracker.database.HabitTypeConverters


@Database(entities = [HabitEntity::class], version = 1)
@TypeConverters(HabitTypeConverters::class)
abstract class HabitDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
}