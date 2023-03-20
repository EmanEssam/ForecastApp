package com.perfectpresentation.forecastapp.data.repository

import com.perfectpresentation.forecastapp.data.local.entities.WeatherDataEntity
import com.perfectpresentation.forecastapp.data.model.ForecastResponse

interface ForecastRepository {
    suspend fun getForecastByLocation(searchKey: String): ForecastResponse<Any>
    suspend fun getForecastSavedData(): WeatherDataEntity.WeatherData

}