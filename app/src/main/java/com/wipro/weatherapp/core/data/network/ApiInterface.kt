package com.wipro.weatherapp.core.data.network

import com.wipro.weatherapp.core.data.WeatherResponse
import com.wipro.weatherapp.core.data.utils.CURRENT
import com.wipro.weatherapp.core.data.utils.QUERY
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {

    // The api's Data  take someTime soo that's why we will be using Defferend
    @GET(CURRENT)
    fun getCurrentWeatherAsync(@Query(QUERY) mLocation: String): Deferred<WeatherResponse>

}