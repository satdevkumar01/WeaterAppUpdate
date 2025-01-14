package com.wipro.weatherapp.data.repository.dataSource

import com.wipro.weatherapp.data.model.WeatherResponse
import retrofit2.Response

interface WeatherDataSource {
    suspend fun fetchWeather(cityName: String): Response<WeatherResponse>

}