package com.perfectpresentation.forecastapp.data.repository

import com.perfectpresentation.forecastapp.BuildConfig
import com.perfectpresentation.forecastapp.data.local.WeatherDataDao
import com.perfectpresentation.forecastapp.data.local.entities.WeatherDataEntity
import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import com.perfectpresentation.forecastapp.data.remote.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val apiInterface: ApiInterface,
    private val weatherDataDao: WeatherDataDao

) : ForecastRepository {


    override suspend fun getForecastByLocation(
        searchKey: String,
        date: String
    ): ForecastResponse<Any> {
        val forecastData = apiInterface.getCurrentWeatherByLocation(
            searchKey,
            BuildConfig.API_KEY,
            startDate = date
        )
        withContext(Dispatchers.IO) {
            val weatherData = WeatherDataEntity.WeatherData(
                weatherDate = forecastData.forecast?.forecastday?.first()?.date!!,
                weatherTime = forecastData.location?.localtime!!,
                location = forecastData.location.country,
                conditionLogo = forecastData.forecast.forecastday.first()?.hour?.first()?.condition?.icon!!,
                weatherCondition = forecastData.forecast.forecastday.first()?.hour?.first()?.condition?.text!!,
                degreeC = forecastData.forecast?.forecastday?.first()?.hour?.first()?.temp_c.toString(),
                degreeF = forecastData.forecast?.forecastday?.first()?.hour?.first()?.temp_f.toString(),
                sunriseTime = forecastData.forecast?.forecastday?.first()?.astro?.sunrise!!,
                sunsetTime = forecastData.forecast?.forecastday?.first()?.astro?.sunset!!,
                windSpeedKph = forecastData.forecast?.forecastday?.first()?.hour?.first()?.wind_kph.toString(),
                windSpeedMph = forecastData.forecast?.forecastday?.first()?.hour?.first()?.wind_kph.toString()
            )
            weatherDataDao.insertWeatherEntity(weatherData = weatherData)
        }
        return forecastData

    }

    override suspend fun getForecastSavedData(): WeatherDataEntity.WeatherData {
        return weatherDataDao.getWeatherData()
    }


}