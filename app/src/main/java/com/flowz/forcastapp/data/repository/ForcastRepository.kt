package com.flowz.forcastapp.data.repository

import androidx.lifecycle.LiveData
import com.flowz.forcastapp.data.db.entity.CurrentWeatherEntry

interface ForcastRepository {

    suspend fun getCurrentWeather():  CurrentWeatherEntry

//    suspend fun getCurrentWeather(yes: Boolean): LiveData< CurrentWeatherEntry>

}