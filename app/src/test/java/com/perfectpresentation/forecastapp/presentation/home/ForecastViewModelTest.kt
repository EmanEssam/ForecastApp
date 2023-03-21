package com.perfectpresentation.forecastapp.presentation.home


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.perfectpresentation.forecastapp.data.model.Forecast
import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import com.perfectpresentation.forecastapp.data.model.Location
import com.perfectpresentation.forecastapp.data.repository.ForecastRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ForecastViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)

    private lateinit var forecastRepository: ForecastRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        forecastRepository = Mockito.mock(ForecastRepository::class.java)
        viewModel = HomeViewModel(forecastRepository)
    }

    @After
    fun tearDown() {
        testCoroutineScope.cleanupTestCoroutines()
    }

    @Test
    fun `test getForecastByLocation success`() = testCoroutineScope.runBlockingTest {
        // Arrange
        val searchKey = "San Francisco"
        val date = "2023-03-21"
        val expectedForecast = ForecastResponse<Any>(
            Forecast(emptyList()), Location("", 5.0, "", 0, 6.6, "San Francisco", "", "")
        ) // Replace with the correct data class and values


        Mockito.`when`(forecastRepository.getForecastByLocation(searchKey, date))
            .thenReturn(expectedForecast)

        val observer = Mockito.mock(Observer::class.java) as Observer<ForecastResponse<Any>>
        viewModel.forecastDataResponse.observeForever(observer)

        // Act
        viewModel.getForecastByLocation(searchKey, date)

        // Assert
        verify(observer).onChanged(expectedForecast)
        viewModel.forecastDataResponse.removeObserver(observer)
    }

}
