package com.wipro.weatherapp.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.wipro.weatherapp.data.utils.Resource
import com.wipro.weatherapp.presentation.viewmodel.WeatherViewModel


@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    var cityName by remember { mutableStateOf("New York") }
    val weatherState by viewModel.weather.observeAsState()

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
        when(weatherState){
            is Resource.Success->{  weatherState?.data?.let { weather ->
                WeatherInfo(weather)
            }}
            is Resource.Error->{}
            is Resource.Loading->{}
            null -> "no"
        }

    }
}