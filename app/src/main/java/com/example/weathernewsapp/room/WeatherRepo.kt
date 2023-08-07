package com.example.weathernewsapp.room

import androidx.room.Room


class WeatherRepo(context: android.content.Context) {
    private val database: WeatherDatabase = Room.databaseBuilder(
        context.applicationContext, WeatherDatabase::class.java, "weather-database"
    ).allowMainThreadQueries().build()

    fun insert(weather: WeatherModel) = database.weatherDao().insertWeather(weather)
    fun getAllWeather() = database.weatherDao().getAllWeather()
    fun deleteAllWeather() = database.weatherDao().deleteAllWeather()
}
