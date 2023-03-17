package com.perfectpresentation.forecastapp.data.repository

import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import com.perfectpresentation.forecastapp.data.remote.ApiInterface
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface
) : ForecastRepository {
    override suspend fun getForecastByLocation(
        searchKey: String,
        apiKey: String
    ): ForecastResponse<Any> {
        return apiInterface.getCurrentWeatherByLocation(searchKey, apiKey)
    }

}