package com.example.weathernewsapp.room

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@androidx.room.Dao
interface WeatherDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: WeatherModel)

    @Query(value = "SELECT * FROM weather_table")
    fun getAllWeather(): Flow<List<WeatherModel>>

    @Query(value = "DELETE FROM weather_table")
    fun deleteAllWeather()
}