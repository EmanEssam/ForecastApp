package com.perfectpresentation.forecastapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.perfectpresentation.forecastapp.data.local.entities.WeatherDataEntity

@Dao
interface WeatherDataDao {
    @Query("SELECT * FROM weather_data")
    fun getWeatherData(): WeatherDataEntity.WeatherData


    @Insert
    fun insertWeatherEntity(weatherData: WeatherDataEntity.WeatherData)


}