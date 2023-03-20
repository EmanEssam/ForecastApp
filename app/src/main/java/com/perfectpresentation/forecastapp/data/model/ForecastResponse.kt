package com.perfectpresentation.forecastapp.data.model

data class ForecastResponse<Any>(
    val forecast: Forecast?,
    val location: Location?
)