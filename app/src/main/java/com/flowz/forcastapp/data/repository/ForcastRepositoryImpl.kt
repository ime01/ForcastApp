package com.flowz.forcastapp.data.repository

import androidx.lifecycle.LiveData
import com.flowz.forcastapp.data.db.CurrentWeatherDao
import com.flowz.forcastapp.data.db.entity.CurrentWeatherEntry
import com.flowz.forcastapp.data.network.WeatherNetworkDataSoucre
import com.flowz.forcastapp.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.io.File
import java.util.*
import javax.xml.transform.Templates

class ForcastRepositoryImpl(private val currentWeatherDao: CurrentWeatherDao, private val weatherNetworkDataSoucre: WeatherNetworkDataSoucre) : ForcastRepository {


    init {
        weatherNetworkDataSoucre.downloadedCurrentWeather.observeForever { newCurrentWeather ->
//            persist
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(): CurrentWeatherEntry {

        initWeatherData()

        return withContext(Dispatchers.IO){
            return@withContext currentWeatherDao.getWeather()
        }
    }

//    override suspend fun getCurrentWeather(yes: Boolean): LiveData<CurrentWeatherEntry> {
//
//        withContext(Dispatchers.IO){
//
//            return@withContext if (yes) currentWeatherDao.getWeather()
//            else currentWeatherDao.getWeather()
//
//        }
//
//    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse){

//        Global scope can be used here because repositories are not affected by lifecycle

        GlobalScope.launch(Dispatchers.IO){

            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)

        }
    }

    private suspend fun initWeatherData(){

        if (isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()
    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSoucre.fetchCurrentWeather("Toronto", Locale.getDefault().language)
    }

    private fun isFetchCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean{

        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}