package com.perfectpresentation.forecastapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.perfectpresentation.forecastapp.data.local.entities.WeatherDataEntity

@Database(entities = [WeatherDataEntity.WeatherData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao
}