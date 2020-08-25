package com.flowz.forcastapp.display.weather.current

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.flowz.forcastapp.data.repository.ForcastRepository

class CurrentWeatherViewModelFactory(private val forecastRepository: ForcastRepository) :ViewModelProvider.NewInstanceFactory(){

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CurrentWeatherViewModel(forecastRepository) as T
    }
}