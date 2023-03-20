package com.perfectpresentation.forecastapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.perfectpresentation.forecastapp.data.local.entities.WeatherDataEntity

@Database(entities = [WeatherDataEntity.WeatherData::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDataDao(): WeatherDataDao
}