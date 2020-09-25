package com.example.dependencyinjection

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.dependencyinjection.viewstate.WeatherViewState
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class WeatherActivity : AppCompatActivity() {

    @Inject
    lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this) //must be called before super.onCreate():

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonGetWeatherData.setOnClickListener {
            weatherViewModel.getWeatherDetails().observe(this, Observer {
                when (it) {
                    is WeatherViewState.Loading -> {
                        textViewResponse.text = "Getting Data"
                        Toast.makeText(this, "Getting Data", Toast.LENGTH_LONG).show()
                    }
                    is WeatherViewState.Data -> {
                        textViewResponse.text = it.data.toString()
                    }
                    is WeatherViewState.Error -> {
                        Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                    }
                }.exhaustive
            })
        }
    }

    val <T> T.exhaustive: T
        get() = this
}
