package com.perfectpresentation.forecastapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perfectpresentation.forecastapp.data.local.entities.WeatherDataEntity
import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import com.perfectpresentation.forecastapp.data.repository.ForecastRepository
import com.perfectpresentation.forecastapp.helper.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val forecastRepository: ForecastRepository) :
    ViewModel() {
    var forecastDataResponse = SingleLiveEvent<ForecastResponse<Any>>()
    var forecastDataError = SingleLiveEvent<String>()
    val foreCastSavedData = SingleLiveEvent<WeatherDataEntity.WeatherData>()

    private val _data = MutableStateFlow<ForecastResponse<Any>>(ForecastResponse(null, null))
    val data: StateFlow<ForecastResponse<Any>> get() = _data

    @ExperimentalCoroutinesApi
    fun getForecastByLocation(searchKey: String,date:String="2023-03-21") =
        viewModelScope.launch {
            try {
                forecastRepository.getForecastByLocation(searchKey,date).let {
                    forecastDataResponse.postValue(it)

                }
            } catch (e: Exception) {
                forecastDataError.postValue(e.message)
            }
        }


    fun getSavedForecastData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                foreCastSavedData.postValue(forecastRepository.getForecastSavedData())
            }
        }
    }
}