package com.perfectpresentation.forecastapp.data.local

import androidx.room.TypeConverter
import com.perfectpresentation.forecastapp.data.model.Condition
import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import com.perfectpresentation.forecastapp.data.model.Forecastday
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.lang.reflect.ParameterizedType

val moshi: Moshi = Moshi.Builder()
    .addLast(KotlinJsonAdapterFactory())
    .build()
val typeForecastDayWSList: ParameterizedType =
    Types.newParameterizedType(MutableList::class.java, ForecastResponse::class.java)
val jsonAdapter: JsonAdapter<List<Forecastday>> = moshi.adapter(typeForecastDayWSList)
val jsonAdapterConditionWS: JsonAdapter<Condition> = moshi.adapter(Condition::class.java)

class Converters {
    @TypeConverter
    fun listForecastDayWSToJsonString(value: List<Forecastday>?): String =
        jsonAdapter.toJson(value)

    @TypeConverter
    fun jsonForecastDayWSStringToList(value: String) = jsonAdapter.fromJson(value)

    @TypeConverter
    fun jsonStringToConditionWS(value: String) = jsonAdapterConditionWS.fromJson(value)
}