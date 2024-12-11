package com.wipro.weatherapp.features.weather.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.wipro.weaterapp.databinding.WeatherScreenActivityBinding
import com.wipro.weatherapp.features.weather.viewmodel.WeatherViewModel


class WeatherScreenActivity : AppCompatActivity() {
    private lateinit var binding: WeatherScreenActivityBinding
    private val weatherViewModel: WeatherViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = WeatherScreenActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupObservers()
        weatherViewModel.loadWeather()
    }


    private fun setupObservers() {
        weatherViewModel.weatherResponse.observe(this) { it ->
            binding.temperature.text = "${it.current?.temperature}°C"
            binding.locationName.text = it.location?.country
            binding.weatherDescription.text = it.current?.weatherDescriptions?.firstOrNull()
            binding.feelsLike.text = "Feels like ${it.current?.feelslike}°C"
            binding.windSpeed.text = "Wind: ${it.current?.windSpeed} km/h ${it.current?.windDir}"
            binding.humidity.text = "Humidity: ${it.current?.humidity}%"
            binding.precipitation.text = "Precipitation: ${it.current?.precip} mm"
            binding.uvIndex.text = "UV Index: ${it.current?.uvIndex}"
            binding.visibility.text = "Visibility: ${it.current?.visibility} km"
            binding.pressure.text = "Pressure: ${it.current?.pressure} hPa"
            binding.observationTime.text = "Observed at: ${it.current?.observationTime}"
            Glide.with(this).load(it.current?.weatherIcons?.get(0).toString())
                .into(binding.weatherIcon)
        }
    }
}