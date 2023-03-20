package com.perfectpresentation.forecastapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.perfectpresentation.forecastapp.data.model.*

class WeatherDataEntity {

    @Entity("weather_data")
    data class WeatherData(
        @PrimaryKey(autoGenerate = true)
        val weatherId: Int = 0,
        val weatherDate: String = "",
        val weatherCondition: String = "",
        val conditionLogo: String = "",
        val location:String="",
        val weatherTime: String = "",
        val degreeF: String = "",
        val degreeC: String = "",
        val windSpeedKph: String = "",
        val windSpeedMph: String = "",
        val sunriseTime: String = "",
        val sunsetTime: String = ""
    )
}