package com.example.myapplication001.model

data class WeatherResponse(
    val name: String,
    val main: MainWeather,
    val weather: List<WeatherDetail>,
    val wind: Wind
)

data class MainWeather(
    val temp: Double,
    val feels_like: Double,
    val humidity: Int
)

data class WeatherDetail(
    val description: String,
    val icon: String
)

data class Wind(
    val speed: Double
)