package com.wipro.weatherapp.domain.repository

import com.wipro.weatherapp.data.model.WeatherResponse
import com.wipro.weatherapp.data.utils.Resource

interface WeatherRepository {
    suspend fun getWeather(cityName: String): Resource<WeatherResponse>
}