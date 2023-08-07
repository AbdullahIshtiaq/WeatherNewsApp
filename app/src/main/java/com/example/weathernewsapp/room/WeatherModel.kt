package com.example.weathernewsapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "weather_table")
data class WeatherModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "name")
    var name: String? = null,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "temp")
    var temp: Int = 0,
    @ColumnInfo(name = "humidity")
    var humidity: Int = 0,
    @ColumnInfo(name = "wind")
    var wind: Double = 0.0,
    @ColumnInfo(name = "dateTime")
    var dateTime: String? = null,
) : Serializable