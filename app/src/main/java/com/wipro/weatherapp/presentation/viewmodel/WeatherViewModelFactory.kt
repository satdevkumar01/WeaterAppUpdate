package com.wipro.weatherapp.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wipro.weatherapp.domain.usecase.GetWeatherUseCase


class WeatherViewModelFactory(
   private val application: Application,
   private val getWeatherUseCase: GetWeatherUseCase
    ) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WeatherViewModel(
            application,
            getWeatherUseCase
        ) as T


    }
}
