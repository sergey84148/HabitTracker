package com.example.habittracker.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.habittracker.Habit
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {

    @Insert
    suspend fun insert(habit: HabitEntity)

    @Update
    suspend fun update(habit: HabitEntity)

    // Удаление привычки по объекту
    @Delete
    suspend fun delete(habit: HabitEntity)

    // Удаление по ID (альтернативный метод)
    @Query("DELETE FROM HabitEntity WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM HabitEntity WHERE id = :id")
    suspend fun getHabitById(id: Int): HabitEntity?

    @Query("SELECT * FROM HabitEntity")
    suspend fun getAllHabits(): List<HabitEntity>

    // Исправлено: возвращает Flow<List<HabitEntity>>
    @Query("SELECT * FROM HabitEntity")
    fun observeAllHabits(): Flow<List<HabitEntity>>

    @Query("SELECT * FROM HabitEntity WHERE id = :habitId")
    fun observeHabit(habitId: Long): LiveData<HabitEntity?>
}