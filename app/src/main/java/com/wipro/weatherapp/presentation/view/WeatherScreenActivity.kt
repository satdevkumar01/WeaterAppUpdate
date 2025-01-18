package com.wipro.weatherapp.presentation.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.wipro.weatherapp.data.model.WeatherResponse
import com.wipro.weatherapp.presentation.viewmodel.WeatherViewModel
import com.wipro.weatherapp.presentation.viewmodel.WeatherViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherScreenActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: WeatherViewModelFactory
    private lateinit var viewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory)[WeatherViewModel::class.java]
        setContent {
            WeatherScreen(viewModel)
        }
    }


    @Composable
    fun WeatherScreen(viewModel: WeatherViewModel) {
        var cityName by remember { mutableStateOf("New York") }
        val weatherState by viewModel.weather.observeAsState()
        val context = LocalContext.current
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = remember { SnackbarHostState() }) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = cityName,
                    onValueChange = {
                        cityName = it
                        if (it.isNotEmpty()) {
                            viewModel.getWeather(it)
                        }
                    },
                    label = { Text("Enter City Name") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
                )

                Spacer(modifier = Modifier.height(20.dp))
                viewModel.weatherResponse.observe(this@WeatherScreenActivity, { respoonse ->


                })
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = cityName,
                onValueChange = {
                    cityName = it
                    if (it.isNotEmpty()) {
                        viewModel.getWeather(it)
                    }
                },
                label = { Text("Enter City Name") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
            )

            Spacer(modifier = Modifier.height(20.dp))

            weatherState?.data?.let { weather ->
                WeatherInfo(weather)
            }
        }
    }

    @Composable
    fun WeatherInfo(weather: WeatherResponse) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${weather.location.name}",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${weather.current.temperature}°C",
                    fontSize = 48.sp,
                    style = MaterialTheme.typography.displayMedium
                )
            }

            weather.current.weather_icons.firstOrNull()?.let { iconUrl ->

            }

            WeatherInfoItem(
                label = "Weather",
                value = weather.current.weather_descriptions.firstOrNull() ?: ""
            )
            WeatherInfoItem(label = "Feels like", value = "${weather.current.feelslike}°C")
            WeatherInfoItem(
                label = "Wind",
                value = "${weather.current.wind_speed} km/h ${weather.current.wind_dir}"
            )
            WeatherInfoItem(label = "Humidity", value = "${weather.current.humidity}%")
            WeatherInfoItem(label = "Precipitation", value = "${weather.current.precip} mm")
            WeatherInfoItem(label = "UV Index", value = "${weather.current.uv_index}")
            WeatherInfoItem(label = "Visibility", value = "${weather.current.visibility} km")
            WeatherInfoItem(label = "Pressure", value = "${weather.current.pressure} hPa")
            WeatherInfoItem(label = "Observed at", value = weather.current.observation_time)
        }
    }

    @Composable
    fun WeatherInfoItem(label: String, value: String) {
        Text(
            text = "$label: $value",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth()
        )
    }
}