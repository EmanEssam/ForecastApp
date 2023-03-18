package com.perfectpresentation.forecastapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perfectpresentation.forecastapp.data.model.Forecastday
import com.perfectpresentation.forecastapp.data.repository.ForecastRepository
import com.perfectpresentation.forecastapp.helper.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val forecastRepository: ForecastRepository) :
    ViewModel() {
    private val forecastDataResponse = SingleLiveEvent<List<Forecastday>>()
    private val photosError = SingleLiveEvent<String>()

    @ExperimentalCoroutinesApi
    fun getForecastByLocation(searchKey: String = "a", apiKey: String) = viewModelScope.launch {
        try {
            forecastRepository.getForecastByLocation(searchKey, apiKey).let {
                forecastDataResponse.postValue(it.forecast.forecastday)

            }
        } catch (e: Exception) {
            photosError.postValue(e.message)
        }
    }


}