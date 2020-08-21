package com.flowz.forcastapp.data.network

import androidx.lifecycle.LiveData
import com.flowz.forcastapp.data.network.response.CurrentWeatherResponse

interface WeatherNetworkDataSoucre {

    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(loaction: String, languageCode:String)
}