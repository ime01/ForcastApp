package com.flowz.forcastapp.display.weather.current

import androidx.lifecycle.ViewModel
import com.flowz.forcastapp.data.repository.ForcastRepository
import com.flowz.forcastapp.internal.UnitSytem
import com.flowz.forcastapp.internal.lazyDeferred

class CurrentWeatherViewModel (private val forecastRepository: ForcastRepository) : ViewModel() {

    private val unitSystem = UnitSytem.YES//get from settings later

    val isBool: Boolean
    get() = unitSystem == UnitSytem.YES


    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }


}
