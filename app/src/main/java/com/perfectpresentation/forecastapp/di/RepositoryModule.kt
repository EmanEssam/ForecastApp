package com.perfectpresentation.forecastapp.di

import com.perfectpresentation.forecastapp.data.repository.ForecastRepository
import com.perfectpresentation.forecastapp.data.repository.ForecastRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun bindPhotosRepository(forecastRepository: ForecastRepositoryImpl): ForecastRepository =
        forecastRepository
}