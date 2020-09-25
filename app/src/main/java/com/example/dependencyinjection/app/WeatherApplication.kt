package com.example.dependencyinjection.app

import android.app.Application
import com.example.dependencyinjection.di.DaggerWeatherComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

// Implement HasAndroidInjector to allow Android components (e.g. activities) to be injected.
// and return
class WeatherApplication : Application(), HasAndroidInjector {

    /**
     * The DispatchingAndroidInjector that we’re injecting into the application class below is just a class that facilitates injecting objects into
     * Android-specific classes (such as activities, fragments, services etc)
     * */
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> {
        return dispatchingAndroidInjector
    }

    /**
     * In our custom application class we must instantiate the generated implementation of AppComponent . The generated class’s name is the
     * interface’s name prefixed with Dagger.
     * Example : Dagger + WeatherComponent = DaggerWeatherComponent
     * */
    override fun onCreate() {
        super.onCreate()

        /**
         * Notice we’re calling inject(this) in onCreate() which is telling Dagger to inject the current instance of our application class. This
         * results in Dagger setting the androidInjector field to something (doesn’t matter what) because it is annotated with @Inject .
         */
        DaggerWeatherComponent.create().inject(this@WeatherApplication)
    }
}