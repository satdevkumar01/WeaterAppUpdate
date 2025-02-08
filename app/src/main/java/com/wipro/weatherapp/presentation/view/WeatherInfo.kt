package com.wipro.weatherapp.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wipro.weatherapp.data.model.WeatherResponse


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
                text = weather.location.name,
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