package com.perfectpresentation.forecastapp.data.remote

import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("forecast.json")
    suspend fun getCurrentWeatherByLocation(
        @Query("key") apiKey: String,
        @Query("q") location: String,
        @Query("days") days: Int = 10
    ):ForecastResponse
}