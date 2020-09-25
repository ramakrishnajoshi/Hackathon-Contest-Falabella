package com.example.dependencyinjection

import com.example.dependencyinjection.model.ApiWeatherData
import io.reactivex.Single
import retrofit2.http.GET

interface WeatherService {

    /*
    * This is dummy response for learning
    {   "cityName" : "Bangalore",
        "currentTemperature" : "82.4°F",
        "maxTemperature" : "93.2°F",
        "windSpeed" : "12Km/h"
    }
    */
    @GET("5ed3cf7c340000650001f4dd")
    fun getWeatherDetails() : Single<ApiWeatherData>

}