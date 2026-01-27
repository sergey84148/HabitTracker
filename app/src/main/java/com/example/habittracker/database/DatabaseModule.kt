package com.example.habittracker.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideHabitDatabase(@ApplicationContext context: Context): HabitDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            HabitDatabase::class.java,
            "habit-tracker-database"
        ).build()
    }

    @Singleton
    @Provides
    fun provideHabitDao(habitDatabase: HabitDatabase): HabitDao {
        return habitDatabase.habitDao()
    }
}