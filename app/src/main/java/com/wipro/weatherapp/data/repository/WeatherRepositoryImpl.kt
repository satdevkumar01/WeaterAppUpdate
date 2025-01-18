package com.wipro.weatherapp.data.repository

import com.wipro.weatherapp.data.model.WeatherResponse
import com.wipro.weatherapp.data.repository.dataSource.WeatherDataSource
import com.wipro.weatherapp.data.utils.Resource
import com.wipro.weatherapp.domain.repository.WeatherRepository
import retrofit2.Response


class WeatherRepositoryImpl(
    private val weatherDataSource: WeatherDataSource,
) : WeatherRepository {


    override suspend fun getWeather(cityName: String): Resource<WeatherResponse> {
        return responseToResource(weatherDataSource.fetchWeather(cityName))
    }

    private fun responseToResource(response: Response<WeatherResponse>):Resource<WeatherResponse>{
        if(response.isSuccessful){
            response.body()?.let {result->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}