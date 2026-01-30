package com.example.habittracker.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.time.DayOfWeek

object HabitTypeConverters {

    @TypeConverter
    @JvmStatic
    fun fromDaysToJson(value: Map<DayOfWeek, Boolean>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun jsonToDays(json: String): Map<DayOfWeek, Boolean> {
        val type: Type = object : TypeToken<Map<DayOfWeek, Boolean>>() {}.type
        return Gson().fromJson(json, type)
    }
}