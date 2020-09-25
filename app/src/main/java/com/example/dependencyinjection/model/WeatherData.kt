package com.example.dependencyinjection.model

data class WeatherData(
    var cityName: String,
    var currentTemperature: String,
    var maxTemperature: String,
    var windSpeed: String
) {

    override fun toString(): String {
        return "City : $cityName\n" +
                "Current Temperature : $currentTemperature\n" +
                "Maximum Day Temperature : $maxTemperature\n" +
                "Wind Speed : $windSpeed"
    }
}