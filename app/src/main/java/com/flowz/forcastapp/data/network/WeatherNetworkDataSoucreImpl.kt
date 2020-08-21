package com.flowz.forcastapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.flowz.forcastapp.data.network.response.CurrentWeatherResponse
import com.flowz.forcastapp.internal.NoConnectivityException
import javax.xml.transform.Templates

class WeatherNetworkDataSoucreImpl (private val weatherstackApiService: WeatherstackApiService): WeatherNetworkDataSoucre {


    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
    get() = _downloadedCurrentWeather


    override suspend fun fetchCurrentWeather(loaction: String, languageCode: String) {

        try {
            val fetchedCurrentWeather = weatherstackApiService.getCurrentWeather(loaction, languageCode).await()
            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)


        }catch (e: NoConnectivityException){
            Log.e("connectivity", "No internet connection", e)
        }



    }
}