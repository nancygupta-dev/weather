package com.example.weather.data

import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("v1/forecast")
    suspend fun getWeather(
        @Query("latitude") latitude:
        Double,
        @Query("longitude") longitude:
        Double,
        @Query("current") current:
        String =
            "temperature_2m,apparent_temperature,relative_humidity_2m," +
                    "wind_speed_10m,weather_code",

        @Query("daily") daily: String =
            "temperature_2m_max,temperature_2m_min,weather_code",

        @Query("forecast_days") forecastDays: Int = 5,

        @Query("timezone") timezone: String = "auto"
    ): WeatherResponse
}