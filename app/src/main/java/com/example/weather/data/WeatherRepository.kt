package com.example.weather.data

class WeatherRepository {

    suspend fun getWeather(
        latitude: Double,
        longitude: Double
    ): WeatherResponse {

        return RetrofitInstance.api.getWeather(
            latitude = latitude,
            longitude = longitude
        )
    }
        suspend fun searchCity(
            city: String
        ): Location? {
            return RetrofitInstance.geocodingApi
                .searchCity(city)
                .results
                ?.firstOrNull()
    }
}