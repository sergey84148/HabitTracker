package com.example.habittracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Insert
    suspend fun insert(habit: HabitEntity)

    @Update
    suspend fun update(habit: HabitEntity)

    @Query("SELECT * FROM HabitEntity WHERE id = :id")
    suspend fun getHabitById(id: Int): HabitEntity?

    @Query("SELECT * FROM HabitEntity")
    suspend fun getAllHabits(): List<HabitEntity>

    @Query("SELECT * FROM HabitEntity")
    fun observeAllHabits(): Flow<HabitEntity>
}