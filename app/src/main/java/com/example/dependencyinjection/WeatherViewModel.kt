package com.example.dependencyinjection

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dependencyinjection.viewstate.WeatherViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

// A constructor annotated with @Inject tells Dagger to use it when trying to instantiate the class.

// @Inject constructor() tells Dagger to use this constructor when it needs to instantiate the class
// class WeatherViewModel @Inject constructor() {
// }


// Dagger will try to instantiate any parameters of the @Injected-annotated constructor and pass them in.
// Notice that we’ve added the repository as a parameter of the constructor that we previously
// annotated with @Inject . Dagger will try to instantiate each of the constructor parameters when
// instantiating the class.
// “whenever someone injects the ApiWeatherRepository interface, go and give them a WeatherRepositoryImpl instance”.
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository) :
    ViewModel() {

    var disposable = CompositeDisposable()

    fun getWeatherDetails(): MutableLiveData<WeatherViewState>{
        val weatherLiveData = MutableLiveData<WeatherViewState>()

        disposable.add(weatherRepository
            .getWeatherDetails()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                weatherLiveData.postValue(it)
            }
        )
        return weatherLiveData
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}
