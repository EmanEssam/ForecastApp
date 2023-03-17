package com.perfectpresentation.forecastapp.data.model

data class ForecastResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)