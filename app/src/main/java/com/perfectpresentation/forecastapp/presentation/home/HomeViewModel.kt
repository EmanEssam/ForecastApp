package com.perfectpresentation.forecastapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import com.perfectpresentation.forecastapp.data.repository.ForecastRepository
import com.perfectpresentation.forecastapp.helper.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val forecastRepository: ForecastRepository) :
    ViewModel() {
    val forecastDataResponse = SingleLiveEvent<ForecastResponse<Any>>()
    val forecastDataError = SingleLiveEvent<String>()

    private val _data = MutableStateFlow<ForecastResponse<Any>>(ForecastResponse(null, null))
    val data: StateFlow<ForecastResponse<Any>> get() = _data

    @ExperimentalCoroutinesApi
    fun getForecastByLocation(searchKey: String) =
        viewModelScope.launch {
            try {
                forecastRepository.getForecastByLocation(searchKey).let {
                    forecastDataResponse.postValue(it)

                }
            } catch (e: Exception) {
                forecastDataError.postValue(e.message)
            }
        }


//    fun getSavedForecastData() {
//        viewModelScope.launch {
//            forecastRepository.getSavedForecast().collect {
//                _data.value = ForecastResponse(it.forecastDb, it.locationDb)
//            }
//        }
//    }
}