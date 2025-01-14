package com.wipro.weatherapp.data

import com.wipro.weatherapp.data.model.WeatherResponse
import com.wipro.weatherapp.data.repository.dataSource.WeatherDataSource
import com.wipro.weatherapp.data.utils.Resource
import com.wipro.weatherapp.domain.repository.WeatherRepository
import retrofit2.Response

class WeatherRepositoryImp(
    private val weatherDataSource: WeatherDataSource
): WeatherRepository {
    override suspend fun getWeather(cityName: String): Resource<WeatherResponse> {
        return responseToResource(weatherDataSource.fetchWeather(cityName))
    }

    private fun responseToResource(fetchWeather: Response<WeatherResponse>): Resource<WeatherResponse> {
        if (fetchWeather.isSuccessful){
            fetchWeather.body()?.let {
                return Resource.Success(it)
            }
        }
        return Resource.Error(fetchWeather.message())

    }
}