package com.example.habittracker.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.DayOfWeek

object HabitTypeConverters {
    private val gson = Gson()


    @TypeConverter
    fun fromDaysToJson(value: Map<DayOfWeek, Boolean>?): String? {
        // Если value == null → возвращаем null
        return value?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun jsonToDays(json: String?): Map<DayOfWeek, Boolean>? {
        // Если json == null → возвращаем null
        return json?.let {
            val type = object : TypeToken<Map<DayOfWeek, Boolean>>() {}.type
            gson.fromJson(it, type)
        }
    }
}
