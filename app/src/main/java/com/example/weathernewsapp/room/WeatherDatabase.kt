package com.example.weathernewsapp.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [WeatherModel::class], version = 1, exportSchema = true)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDAO
}