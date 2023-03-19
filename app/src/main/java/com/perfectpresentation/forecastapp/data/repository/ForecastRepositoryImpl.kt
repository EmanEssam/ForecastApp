package com.perfectpresentation.forecastapp.data.repository

import com.perfectpresentation.forecastapp.BuildConfig
import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import com.perfectpresentation.forecastapp.data.remote.ApiInterface
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ForecastRepository {
    override suspend fun getForecastByLocation(
        searchKey: String
    ): ForecastResponse<Any> {
        return apiInterface.getCurrentWeatherByLocation(searchKey,BuildConfig.API_KEY)
    }

}