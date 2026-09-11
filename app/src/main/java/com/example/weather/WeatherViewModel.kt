package com.example.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.WeatherRepository
import com.example.weather.data.WeatherResponse
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    var weather: WeatherResponse? by
    mutableStateOf(null)
        private set

    var isLoading: Boolean by
    mutableStateOf(false)
        private set

    var errorMessage: String? by
    mutableStateOf(null)
        private set

    fun searchWeather(city: String) {
        viewModelScope.launch {

            isLoading = true
            errorMessage = null

            try {
                val location = repository.searchCity(city)
                if (location != null) {
                    weather = repository.getWeather(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                } else {
                    errorMessage = "City not found"
                }

            } catch (e: Exception) {
                errorMessage = "Something went wrong"
            }

            isLoading = false
        }
    }
}