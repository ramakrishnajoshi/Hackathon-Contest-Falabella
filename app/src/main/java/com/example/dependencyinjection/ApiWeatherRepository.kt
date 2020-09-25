package com.example.dependencyinjection

import com.example.dependencyinjection.viewstate.WeatherViewState
import io.reactivex.Observable

interface ApiWeatherRepository {
    fun getWeatherDetails(): Observable<WeatherViewState>
}