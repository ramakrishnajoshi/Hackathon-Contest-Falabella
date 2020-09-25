package com.example.dependencyinjection.di

import com.example.dependencyinjection.app.WeatherApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, WeatherModule::class])
interface WeatherComponent : AndroidInjector<WeatherApplication>{

}