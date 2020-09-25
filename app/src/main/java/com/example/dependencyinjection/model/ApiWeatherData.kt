package com.example.dependencyinjection.model

data class ApiWeatherData(
    val cityName: String?,
    val currentTemperature: String?,
    val maxTemperature: String?,
    val windSpeed: String?
)