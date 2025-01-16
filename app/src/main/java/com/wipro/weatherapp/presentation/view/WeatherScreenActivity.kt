package com.wipro.weatherapp.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.wipro.weaterapp.databinding.WeatherScreenActivityBinding
import com.wipro.weatherapp.data.model.WeatherResponse
import com.wipro.weatherapp.data.utils.Resource
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
        val context = LocalContext.current
        var isLoading by remember { mutableStateOf(false) }

        val weatherState = viewModel.weatherResponse

        LaunchedEffect(weatherState) {
            when (weatherState) {
                is Resource.Success<*> -> isLoading = false
                is Resource.Error<*> -> {
                    isLoading = false
                    Toast.makeText(
                        context,
                        "An error occurred: ${(weatherState as Resource.Error<*>).message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                is Resource.Loading<*> -> isLoading = true
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

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            weatherState.value?.let { weather ->
                weather.data?.let { WeatherInfo(it) }
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
                    text = "${weather.location?.name}, ${weather.location?.country}",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = "${weather.current?.temperature}°C",
                    fontSize = 48.sp,
                    style = MaterialTheme.typography.displayMedium
                )
            }

            weather.current?.weatherIcons?.firstOrNull()?.let { iconUrl ->
               /* AsyncImage(
                    model = iconUrl,
                    contentDescription = "Weather Icon",
                    modifier = Modifier.size(100.dp)
                )*/
            }

            WeatherInfoItem(label = "Weather", value = weather.current?.weatherDescriptions?.firstOrNull() ?: "")
            WeatherInfoItem(label = "Feels like", value = "${weather.current?.feelslike}°C")
            WeatherInfoItem(label = "Wind", value = "${weather.current?.windSpeed} km/h ${weather.current?.windDir}")
            WeatherInfoItem(label = "Humidity", value = "${weather.current?.humidity}%")
            WeatherInfoItem(label = "Precipitation", value = "${weather.current?.precip} mm")
            WeatherInfoItem(label = "UV Index", value = "${weather.current?.uvIndex}")
            WeatherInfoItem(label = "Visibility", value = "${weather.current?.visibility} km")
            WeatherInfoItem(label = "Pressure", value = "${weather.current?.pressure} hPa")
            WeatherInfoItem(label = "Observed at", value = weather.current?.observationTime ?: "")
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