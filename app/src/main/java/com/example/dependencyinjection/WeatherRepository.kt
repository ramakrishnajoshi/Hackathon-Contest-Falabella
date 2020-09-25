package com.example.dependencyinjection

import com.example.dependencyinjection.viewstate.WeatherViewState
import com.example.dependencyinjection.viewstate.WeatherViewState.Loading
import com.google.gson.JsonParseException
import io.reactivex.Observable
import retrofit2.HttpException
import java.lang.NumberFormatException
import java.net.UnknownHostException
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherService: WeatherService,
    private val weatherConverter: WeatherDataViewStateConverter) : ApiWeatherRepository {

    override fun getWeatherDetails(): Observable<WeatherViewState> {
        val loadingViewState = Observable.just(Loading)

        return weatherService
            .getWeatherDetails()
            .map(weatherConverter)
            .toObservable()
            .startWith(loadingViewState)
            .onErrorResumeNext { t: Throwable -> Observable.just(convertToCause(t)) }
    }

    private fun convertToCause(t: Throwable): WeatherViewState {
        return when (t) {
            is JsonParseException -> WeatherViewState.Error("UNEXPECTED_JSON_ERROR")
            is HttpException -> {
                // non-2xx HTTP response
                when ((t as HttpException).code()) {
                    400 -> WeatherViewState.Error("400")
                    401 -> WeatherViewState.Error("401")
                    500 -> WeatherViewState.Error("Internal Server Error")
                    else -> WeatherViewState.Error("SERVER_ERROR")
                }
            }
            is UnknownHostException -> WeatherViewState.Error("NO_INTERNET_CONNECTION")
            is NumberFormatException -> WeatherViewState.Error("Data Error")
            else -> WeatherViewState.Error("UNKNOWN_ERROR")
        }
    }
}