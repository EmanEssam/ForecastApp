package com.perfectpresentation.forecastapp.data.remote

import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("forecast.json")
    suspend fun getCurrentWeatherByLocation(
        @Query("q") location: String,
        @Query("key") apiKey: String,
        @Query("days") days: Int = 5
    ): ForecastResponse<Any>
}