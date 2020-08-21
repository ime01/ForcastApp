package com.flowz.forcastapp.data.db

import androidx.room.*
import com.flowz.forcastapp.data.db.entity.CURRENT_WEATHER_ID
import com.flowz.forcastapp.data.db.entity.Converters
import com.flowz.forcastapp.data.db.entity.CurrentWeatherEntry


@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @TypeConverters(Converters::class)
    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeather(): CurrentWeatherEntry


}