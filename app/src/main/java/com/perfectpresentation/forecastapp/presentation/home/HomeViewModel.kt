package com.perfectpresentation.forecastapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.perfectpresentation.forecastapp.BuildConfig
import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import com.perfectpresentation.forecastapp.data.repository.ForecastRepository
import com.perfectpresentation.forecastapp.helper.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val forecastRepository: ForecastRepository) :
    ViewModel() {
    val forecastDataResponse = SingleLiveEvent<ForecastResponse<Any>>()
    val photosError = SingleLiveEvent<String>()

    @ExperimentalCoroutinesApi
    fun getForecastByLocation(searchKey: String = "48.8567,2.3508") =
        viewModelScope.launch {
            try {
                forecastRepository.getForecastByLocation(searchKey).let {
                    forecastDataResponse.postValue(it)

                }
            } catch (e: Exception) {
                photosError.postValue(e.message)
            }
        }


}