package com.example.myapplication001.data.repository

import com.example.myapplication001.data.remote.WeatherRetrofitInstance
import com.example.myapplication001.model.WeatherResponse

class WeatherRepository {

    private val apiKey = "ba1198654b03128a5bd12699d91d5f0f" // pega tu key de openweathermap.org

    suspend fun getWeather(cityName: String): WeatherResponse {
        return WeatherRetrofitInstance.api.getWeatherByCity(
            cityName = cityName,
            apiKey = apiKey
        )
    }
}