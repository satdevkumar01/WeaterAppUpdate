package com.wipro.weatherapp.data.model

data class WeatherResponse(
    val current: Current,
    val location: Location,
    val request: Request
)