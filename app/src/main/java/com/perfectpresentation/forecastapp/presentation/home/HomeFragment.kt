package com.perfectpresentation.forecastapp.presentation.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.perfectpresentation.forecastapp.R
import com.perfectpresentation.forecastapp.data.model.ForecastResponse
import com.perfectpresentation.forecastapp.data.model.Forecastday
import com.perfectpresentation.forecastapp.databinding.FragmentHomeBinding
import com.perfectpresentation.forecastapp.presentation.home.adapter.DayWeatherAdapter
import com.perfectpresentation.forecastapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val homeViewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestLocationPermission()

    }

    private fun requestLocationPermission() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is granted, get the last known location
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    // Got last known location. In some rare situations this can be null.
                    if (location != null) {
                        getForecastDataByLocation(
                            location.latitude.toString(),
                            location.longitude.toString()
                        )
                    } else {
                        getDefaultLocationWeather()
                    }
                }
        } else {
            // Permission is not granted, request it
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            getDefaultLocationWeather()
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getForecastDataByLocation(lat: String, lng: String) {
        lifecycleScope.launch {
            homeViewModel.getForecastByLocation(
                "$lat,$lng"
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        if (isInternetConnected()) {
            setUpObserver()
            handleSearchViewByCityName()
        } else {
            hideProgressDialog()
            homeViewModel.getSavedForecastData()
            bindOfflineData()
        }
    }

    private fun bindOfflineData() {
        homeViewModel.foreCastSavedData.observe(this, Observer {
            showHomeViews()
            Glide.with(requireContext())
                .load("https:" + it.conditionLogo)
                .into(binding.weatherIc)
            binding.tvDate.text = it.weatherTime
            binding.tvCity.text = it.location
            binding.tvSunDown.text = it.sunsetTime
            binding.tvSunRise.text = it.sunriseTime
            (it.degreeC + "°C").also { binding.tvDegree.text = it }
            (it.windSpeedKph
                .toString() + " Kph").also { binding.tvWind.text = it }
        })
    }


    private fun setUpObserver() {
        homeViewModel.forecastDataResponse.observe(this, Observer {
            hideProgressDialog()
            showHomeViews()
            it.forecast?.forecastday?.let { it1 -> setUpWeatherDataList(it1) }
            bindDataToViews(it)
        })
        homeViewModel.forecastDataError.observe(this, Observer
        {
            hideProgressDialog()
            showLongToast(it)
        })
    }

    private fun bindDataToViews(response: ForecastResponse<Any>) {
        Glide.with(requireContext())
            .load("https:" + response.forecast?.forecastday?.first()?.day?.condition?.icon)
            .into(binding.weatherIc)
        binding.tvDate.text = response.location?.localtime
        binding.tvCity.text = response.location?.country
        binding.tvSunDown.text = response.forecast?.forecastday?.first()?.astro?.sunset
        binding.tvSunRise.text = response.forecast?.forecastday?.first()?.astro?.sunrise
        (response.forecast?.forecastday?.first()?.day?.maxtemp_c?.toInt()
            .toString() + "°C").also { binding.tvDegree.text = it }
        (response.forecast?.forecastday?.first()?.hour?.first()?.wind_kph?.toInt()
            .toString() + " Kph").also { binding.tvWind.text = it }
    }

    private fun setUpWeatherDataList(list: List<Forecastday>) {
        val adapter = DayWeatherAdapter(list) {
            navigateToDetailsScreen(it)
        }

        binding.weatherDaysRV.layoutManager = LinearLayoutManager(requireContext())
        binding.weatherDaysRV.adapter = adapter
    }

    private fun navigateToDetailsScreen(it: Forecastday) {
        val navController = Navigation.findNavController(requireView())
        navController.navigate(R.id.action_homeFragment_to_weatherDetailsFragment)
    }

    private fun handleSearchViewByCityName() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    showProgressDialog()
                    homeViewModel.getForecastByLocation(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText?.length!! > 3) {
                    homeViewModel.getForecastByLocation(newText)
                }
                return true
            }
        })
    }

    private fun hideProgressDialog() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressDialog() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showHomeViews() {
        binding.icSunDown.visibility = View.VISIBLE
        binding.tvSunDown.visibility = View.VISIBLE
        binding.tvSunRise.visibility = View.VISIBLE
        binding.weatherDaysRV.visibility = View.VISIBLE
        binding.icSunRise.visibility = View.VISIBLE
        binding.tvCity.visibility = View.VISIBLE
        binding.tvDate.visibility = View.VISIBLE
        binding.tvTime.visibility = View.VISIBLE
        binding.tvDegree.visibility = View.VISIBLE
        binding.tvWind.visibility = View.VISIBLE
        binding.weatherIc.visibility = View.VISIBLE

    }

    private fun showLongToast(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getDefaultLocationWeather() {
        lifecycleScope.launch {
            homeViewModel.getForecastByLocation(
                Constants.DEFAULT_lATITUDE + "," + Constants.DEFAULT_lONGITUDE
            )
        }
    }

    private fun isInternetConnected(): Boolean {
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
    }
}