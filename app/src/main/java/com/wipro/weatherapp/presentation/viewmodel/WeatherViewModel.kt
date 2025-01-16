package com.wipro.weatherapp.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wipro.weatherapp.data.model.WeatherResponse
import com.wipro.weatherapp.data.utils.Resource
import com.wipro.weatherapp.domain.usecase.GetWeatherUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WeatherViewModel(
   private val application: Application,
   private val getWeatherUseCase: GetWeatherUseCase
    ) : AndroidViewModel(application) {

     val weatherResponse: MutableLiveData<Resource<WeatherResponse>> = MutableLiveData()
    fun getWeather(city:String) = viewModelScope.launch(Dispatchers.IO) {
        weatherResponse.postValue(Resource.Loading())
        if (isNetworkAvailable(application)) {
            val apiResult = getWeatherUseCase.execute(city)
            weatherResponse.postValue(apiResult)
        } else {
            weatherResponse.postValue(Resource.Error("Internet is not available"))
        }
    }
    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }

                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            } else return false
        } else return false
        return false
    }

}
