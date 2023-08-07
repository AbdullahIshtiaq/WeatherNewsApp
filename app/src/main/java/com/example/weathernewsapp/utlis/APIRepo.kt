package com.example.weathernewsapp.utlis

import com.example.weathernewsapp.model.WeatherResponse
import com.example.weathernewsapp.utlis.Constant.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class APIRepository {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    private val weatherService: WeatherService = retrofit.create(WeatherService::class.java)


    suspend fun getWeather(lat: String, lon: String): WeatherResponse {
        return weatherService.getWeather("a2d03888b78340abfb808a6f76245686", lat, lon)

    }

}


interface WeatherService {
    @GET("weather")
    suspend fun getWeather(
        @Query("appid") id: String,
        @Query("lat") lat: String,
        @Query("lon") lon: String
    ): WeatherResponse
}