package com.example.weathernewsapp.utlis

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.core.app.ActivityCompat

interface LocationRepository {
    fun getCurrentLocation(callback: (Location?) -> Unit)
}

class LocationRepositoryImpl(private val context: Context) : LocationRepository {

    private val locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    override fun getCurrentLocation(callback: (Location?) -> Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            callback(null)
            return
        }

        val locationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                callback(location)
                locationManager.removeUpdates(this)
            }

            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                // Unused method
            }

            override fun onProviderEnabled(provider: String) {
                // Unused method
            }

            override fun onProviderDisabled(provider: String) {
                // Unused method
            }


        }

        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, locationListener, null)
    }
}
