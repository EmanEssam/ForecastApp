package com.perfectpresentation.forecastapp.presentation.WeatherDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.perfectpresentation.forecastapp.R
import com.perfectpresentation.forecastapp.databinding.FragmentHomeBinding
import com.perfectpresentation.forecastapp.databinding.FragmentWeatherDetailsBinding
import com.perfectpresentation.forecastapp.presentation.home.HomeViewModel
import com.perfectpresentation.forecastapp.presentation.home.adapter.DayWeatherAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment() {
    private lateinit var binding: FragmentWeatherDetailsBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWeatherDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setUpObserver()
    }

    private fun setUpObserver() {
        lifecycleScope.launch {
            homeViewModel.getForecastByLocation()
        }
        homeViewModel.forecastDataResponse.observe(this, Observer {
            Glide.with(requireContext())
                .load("https:" + it.forecast.forecastday.first().day.condition.icon)
                .into(binding.weatherIc)
            binding.tvDate.text = it.location.localtime
            binding.tvCity.text = it.location.country
            binding.tvTime.text = it.forecast.forecastday.first().day.condition.text
            binding.tvSunDown.text = it.forecast.forecastday.first().astro.sunset
            binding.tvSunRise.text = it.forecast.forecastday.first().astro.sunrise
            (it.forecast.forecastday.first().day.maxtemp_c.toInt()
                .toString() + "°C").also { binding.tvDegree.text = it }

            (it.forecast.forecastday.first().hour.first().wind_kph.toInt()
                .toString() + "Kph").also { binding.tvWind.text = it }

        })

    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WeatherDetailsFragment().apply {

            }
    }
}