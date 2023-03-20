package com.perfectpresentation.forecastapp.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.perfectpresentation.forecastapp.data.model.Forecast
import com.perfectpresentation.forecastapp.data.model.Location

@Entity
data class CurrentWeatherDB(
    @PrimaryKey(autoGenerate = true) val id: Int? = 0,
    @Embedded val forecastDb: Forecast,
    @Embedded val locationDb: Location,
)