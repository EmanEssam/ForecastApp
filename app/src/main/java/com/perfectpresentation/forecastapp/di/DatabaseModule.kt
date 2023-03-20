package com.perfectpresentation.forecastapp.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.perfectpresentation.forecastapp.data.local.CurrentWeatherDao
import com.perfectpresentation.forecastapp.data.local.CurrentWeatherDatabase
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
    fun provideAppDatabase(@ApplicationContext appContext: Context): CurrentWeatherDatabase {
        return Room.databaseBuilder(
            appContext,
            CurrentWeatherDatabase::class.java,
            "currentWeatherDB"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideCurrentWeatherDao(currentWeatherDatabase: CurrentWeatherDatabase): CurrentWeatherDao {
        return currentWeatherDatabase.currentWeatherDao
    }
}