package com.perfectpresentation.forecastapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao {
    @Query("select * from currentweatherdb LIMIT 1")
    fun getCurrentWeather(): Flow<CurrentWeatherDB>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrentWeather(currentWeatherDB: CurrentWeatherDB)
}

@Database(entities = [CurrentWeatherDB::class], version = 1)
@TypeConverters(Converters::class)
abstract class CurrentWeatherDatabase : RoomDatabase() {
    abstract val currentWeatherDao: CurrentWeatherDao
}