package com.example.dependencyinjection.viewstate

import com.example.dependencyinjection.model.WeatherData

sealed class WeatherViewState {

    object Loading : WeatherViewState()
    data class Data(val data : WeatherData) : WeatherViewState()
    data class Error(/*val type : ErrorType, */val message : String) : WeatherViewState()
}