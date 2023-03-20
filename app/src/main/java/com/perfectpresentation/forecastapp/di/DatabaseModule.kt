package com.perfectpresentation.forecastapp.di

import android.content.Context
import androidx.room.Room
import com.perfectpresentation.forecastapp.data.local.AppDatabase
import com.perfectpresentation.forecastapp.data.local.WeatherDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "currentWeatherDB"
        ).build()
    }

    @Provides
    fun provideCurrentWeatherDao(currentWeatherDatabase: AppDatabase): WeatherDataDao {
        return currentWeatherDatabase.weatherDataDao()
    }

}