package com.flowz.forcastapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.flowz.forcastapp.data.db.entity.Converters
import com.flowz.forcastapp.data.db.entity.CurrentWeatherEntry

@Database(entities = [CurrentWeatherEntry::class], version = 1)
@TypeConverters(Converters::class)
abstract class ForecastDatabase: RoomDatabase(){

    abstract fun currentWeatherDao(): CurrentWeatherDao

    companion object{

        @Volatile private var instance : ForecastDatabase? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }


        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext, ForecastDatabase::class.java, "forcast.db").build()

    }


}