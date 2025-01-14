package com.wipro.weatherapp.domain.usecase

import com.wipro.weatherapp.data.model.WeatherResponse
import com.wipro.weatherapp.data.utils.Resource
import com.wipro.weatherapp.domain.repository.WeatherRepository

class GetWeatherUseCase(
    private val weatherRepository: WeatherRepository
) {
    suspend fun execute(cityName: String): Resource<WeatherResponse> {
        return weatherRepository.getWeather(cityName)
    }
}