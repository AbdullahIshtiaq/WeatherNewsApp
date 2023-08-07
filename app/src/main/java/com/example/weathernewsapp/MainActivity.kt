package com.example.weathernewsapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weathernewsapp.databinding.MainActivityLayoutBinding
import com.example.weathernewsapp.utlis.NetworkUtils
import com.example.weathernewsapp.viewModel.WeatherVM
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {
    private lateinit var binding: MainActivityLayoutBinding
    private lateinit var viewModel: WeatherVM
    private var internetAvailable by Delegates.notNull<Boolean>()
    private var locationEnabled by Delegates.notNull<Boolean>()

    private lateinit var locationRequest: LocationRequest
    private val fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = WeatherVM(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
        binding.viewModel = viewModel
        binding.weatherRecyclerView.layoutManager = LinearLayoutManager(this)

        internetAvailable = NetworkUtils.isInternetAvailable(this)
        locationEnabled = NetworkUtils.isLocationEnabled(this)

        if (!internetAvailable) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
        }

        binding.saveBtn.setOnClickListener {
            createLocationRequest()
            if (locationEnabled && internetAvailable) {
                viewModel.getWeather()
            } else {
                Toast.makeText(this, "Please enable location", Toast.LENGTH_SHORT).show()
            }
        }

        binding.deleteBtn.setOnClickListener {
            viewModel.deleteAllWeather()
        }
    }


    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 1000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        startLocationUpdates()
    }


    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Please enable location permission", Toast.LENGTH_SHORT).show()
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
            return
        } else {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult.lastLocation?.let { location ->
                viewModel.latitute.value = location.latitude.toString()
                viewModel.longitute.value = location.longitude.toString()
            }
        }
    }
}