package com.flowz.forcastapp.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    val cloudcover: Int,
    val feelslike: Int,
    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("observation_time")
    val observationTime: String,
    val precip: Double,
    val temperature: Int,
    val visibility: Int,
    @SerializedName("weather_code")
    val weatherCode: Int,

    @SerializedName("weather_descriptions")
    val weatherDescriptions: List<String>,

    @SerializedName("weather_icons")
    val weatherIcons: List<String>,

    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Int
){
    @PrimaryKey(autoGenerate = false)
    var id: Int = CURRENT_WEATHER_ID
}



class Converters {

    @TypeConverter
    fun stringAsStringList(strings: String?): List<String>{

        val list = mutableListOf<String>()
        strings
            ?.split(",")
            ?.forEach{
                list.add(it)
            }

        return list
    }
//    fun listToJson(value: List<CurrentWeatherEntry>?) = Gson().toJson(value)

    @TypeConverter
    fun stringListAsString(strings: List<String>): String{

        var result = ""
        strings?.forEach{element->
            result += "$element,"

        }
        return result.removeSuffix(",")
    }
//    fun jsonToList (value: String) = Gson().toJson(value, Array<CurrentWeatherEntry>::class.java).toList()

}


