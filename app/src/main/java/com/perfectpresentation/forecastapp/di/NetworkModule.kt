package com.perfectpresentation.forecastapp.di

import android.content.Context
import com.perfectpresentation.forecastapp.data.remote.ApiInterface
import com.perfectpresentation.forecastapp.helper.RetrofitFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideForecastApi(
        remoteDataSource: RetrofitFactory,
        @ApplicationContext context: Context
    ): ApiInterface {
        return remoteDataSource.getService()
    }

}
