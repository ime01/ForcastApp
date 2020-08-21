package com.flowz.forcastapp.data.network.response

import com.flowz.forcastapp.data.db.entity.CurrentWeatherEntry
import com.flowz.forcastapp.data.db.entity.Location
import com.flowz.forcastapp.data.db.entity.Request
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(

    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,

    val location: Location,

    val request: Request
)