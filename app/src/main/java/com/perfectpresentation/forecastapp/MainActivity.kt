package com.perfectpresentation.forecastapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.perfectpresentation.forecastapp.databinding.ActivityMainBinding
import com.perfectpresentation.forecastapp.presentation.home.HomeViewModel
import com.perfectpresentation.forecastapp.presentation.home.adapter.DayWeatherAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    //    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        initViews()
        setupRecyclerView()
        setUpObserver()
    }

    private fun setUpObserver() {
    }

    private fun setupRecyclerView() {

    }

    private fun initViews() {

    }

    private fun hideProgressDialog() {
//        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressDialog() {
//        binding.progressBar.visibility = View.VISIBLE
    }

    private fun showLongToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }
}