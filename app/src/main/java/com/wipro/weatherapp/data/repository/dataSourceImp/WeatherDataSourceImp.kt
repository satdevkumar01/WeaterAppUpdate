package com.wipro.weatherapp.data.repository.dataSourceImp

import com.wipro.weatherapp.data.api.WeatherAPIService
import com.wipro.weatherapp.data.model.WeatherResponse
import com.wipro.weatherapp.data.repository.dataSource.WeatherDataSource
import retrofit2.Response

class WeatherDataSourceImp(
    private val weatherService: WeatherAPIService)
    : WeatherDataSource {
    override suspend fun fetchWeather(cityName: String): Response<WeatherResponse> {
        return weatherService.getCurrentWeatherAsync(cityName)
    }
}