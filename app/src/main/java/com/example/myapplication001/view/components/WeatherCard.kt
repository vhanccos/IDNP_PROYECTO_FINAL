package com.example.myapplication001.view.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication001.viewmodel.WeatherUiState
import com.example.myapplication001.viewmodel.WeatherViewModel

@Composable
fun WeatherCard(
    cityName: String,
    viewModel: WeatherViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(cityName) {
        viewModel.loadWeather(cityName)
    }

    Card(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.padding(16.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
            when (val state = uiState) {
                is WeatherUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp))
                }
                is WeatherUiState.Success -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Clima en ${state.data.name}", fontWeight = FontWeight.Bold)
                        Text("${state.data.main.temp}°C - ${state.data.weather.firstOrNull()?.description ?: ""}")
                        Text("Humedad: ${state.data.main.humidity}%  Viento: ${state.data.wind.speed} m/s")
                    }
                }
                is WeatherUiState.Error -> {
                    Text("No se pudo cargar el clima: ${state.message}")
                }
            }
        }
    }
}