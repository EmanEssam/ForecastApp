package com.perfectpresentation.forecastapp.data.repository

import com.perfectpresentation.forecastapp.BuildConfig
import com.perfectpresentation.forecastapp.data.local.CurrentWeatherDB
import com.perfectpresentation.forecastapp.data.local.CurrentWeatherDatabase
import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import com.perfectpresentation.forecastapp.data.remote.ApiInterface
import com.perfectpresentation.forecastapp.utils.DataTransformData.asDatabaseModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val database: CurrentWeatherDatabase,
) : ForecastRepository {

    private val currentWeather: Flow<CurrentWeatherDB> =
        database.currentWeatherDao.getCurrentWeather()

    override suspend fun getForecastByLocation(
        searchKey: String
    ): ForecastResponse<Any> {
        val currentWeather =
            apiInterface.getCurrentWeatherByLocation(searchKey, BuildConfig.API_KEY)
        database.currentWeatherDao.insertCurrentWeather(currentWeather.asDatabaseModel())
        return currentWeather
    }

}