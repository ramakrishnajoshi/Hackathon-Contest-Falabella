package com.example.dependencyinjection

import androidx.core.util.toHalf
import com.example.dependencyinjection.model.ApiWeatherData
import com.example.dependencyinjection.model.WeatherData
import com.example.dependencyinjection.viewstate.WeatherViewState
import io.reactivex.functions.Function

class WeatherDataViewStateConverter : Function<ApiWeatherData, WeatherViewState>{

    override fun apply(apiData: ApiWeatherData): WeatherViewState {
        if (apiData != null){
            val weatherData = WeatherData("","","","")
            apiData.cityName?.let {
                weatherData.cityName = it
            }

            apiData.currentTemperature?.let {
                weatherData.currentTemperature = fahrenheitToCelsius(it)
            }

            apiData.maxTemperature?.let {
                weatherData.maxTemperature = fahrenheitToCelsius(it)
            }

            apiData.windSpeed?.let {
                if (it.isEmpty()){
                    weatherData.windSpeed = "N/A"
                }
                weatherData.windSpeed = it
            }
            return WeatherViewState.Data(weatherData)
        } else{
            return WeatherViewState.Error("Null Response")
        }
    }

    private fun fahrenheitToCelsius(value : String) : String{
        val temperature = value.replace("°F", "")
        if (!temperature.isNullOrEmpty()){
            val temperatureInCelcius = (((temperature.toDouble() - 32)*5)/9).toString() + " °C"
            return temperatureInCelcius
        }
        return ""
    }
}
