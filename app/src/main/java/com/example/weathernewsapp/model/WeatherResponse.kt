package com.example.weathernewsapp.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("weather") val weather: List<Weather>,
    @SerializedName("main") val main: Main,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    var dateTime: String? = null

)

data class Weather(
    @SerializedName("description") val description: String,
)

data class Main(
    @SerializedName("temp") val temp: Double,
    @SerializedName("humidity") val humidity: Int
)


data class Wind(
    @SerializedName("speed") val speed: Double,
)