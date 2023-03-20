package com.perfectpresentation.forecastapp.utils

import com.perfectpresentation.forecastapp.data.local.CurrentWeatherDB
import com.perfectpresentation.forecastapp.data.model.Forecast
import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import com.perfectpresentation.forecastapp.data.model.Forecastday
import com.perfectpresentation.forecastapp.data.model.Location

object DataTransformData {
    fun CurrentWeatherDB.asCurrentWeatherDomainModel(): Forecast {
        return Forecast(
            emptyList()
        )
    }
    fun ForecastResponse<Any>.asDatabaseModel(): CurrentWeatherDB {
        return CurrentWeatherDB(
            forecastDb = forecast,
            locationDb = location

        )
    }
}