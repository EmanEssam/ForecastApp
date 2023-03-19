package com.perfectpresentation.forecastapp.data.remote

import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("history.json")
    suspend fun getCurrentWeatherByLocation(
        @Query("q") location: String,
        @Query("key") apiKey: String,
        @Query("days") days: Int = 5,
        @Query("dt") startDate: String = "2023-03-17",
        @Query("end_dt") endDate: String = "2023-03-21",
    ): ForecastResponse<Any>
}