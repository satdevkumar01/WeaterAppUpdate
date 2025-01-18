package com.wipro.weatherapp.data.api

import com.wipro.weaterapp.BuildConfig
import com.wipro.weatherapp.data.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherAPIService {

    // The api's Data  take someTime soo that's why we will be using Defferend
    @GET("current")
    suspend fun getCurrentWeatherAsync(
        @Query("query") cityName: String,
        @Query("access_key")
        apiKey:String= BuildConfig.API_KEY
    ): Response<WeatherResponse>

}