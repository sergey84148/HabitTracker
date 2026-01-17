package com.example.habittracker

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface HabitRepository {
    fun getAll(): Flow<List<Habit>>
    suspend fun insert(habit: Habit)
    suspend fun delete(habit: Habit)
    suspend fun update(habit: Habit)
}

class InMemoryHabitRepository @Inject constructor() : HabitRepository {
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

@InstallIn(SingletonComponent::class)
@Module
interface HabitRepositoryModule {
    @Binds
    fun bindInMemoryHabitRepository(impl: InMemoryHabitRepository): HabitRepository
}
