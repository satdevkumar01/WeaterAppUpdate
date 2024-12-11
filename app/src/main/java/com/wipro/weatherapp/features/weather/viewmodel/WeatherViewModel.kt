package com.wipro.weatherapp.features.weather.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wipro.weatherapp.core.data.network.ApiInterface
import com.wipro.weatherapp.core.data.network.RetrofitClient
import com.wipro.weatherapp.core.data.utils.DEFAULT_LOCATION
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.wipro.weatherapp.core.data.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class WeatherViewModel( application: Application) : AndroidViewModel(application) {

    private var currentLocation: Location? = null
    var cityName = MutableLiveData<String>()
    val weatherResponse = MutableLiveData<WeatherResponse>()

    fun loadWeather() {
        viewModelScope.launch {
            if (
                isNetworkAvailable()
            ) {
                weatherApi()
            }
        }
    }
    private suspend fun weatherApi() {
        val client: ApiInterface = RetrofitClient.service
         weatherResponse.value = client.getCurrentWeatherAsync(cityName.value.orEmpty().ifEmpty { DEFAULT_LOCATION }).await()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    internal fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}
