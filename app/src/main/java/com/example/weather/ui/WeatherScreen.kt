package com.example.weather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weather.WeatherViewModel
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel()
) {

    var city by remember {
        mutableStateOf("")
    }

    val weather = viewModel.weather

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "🌤 Weather App",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1E3A5F)
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        OutlinedTextField(
            value = city,
            onValueChange = {
                city = it
            },
            label = {
                Text("Enter City")
            },
            placeholder = {
                Text("e.g. Delhi")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {
                if (city.isNotBlank()) {
                    viewModel.searchWeather(city)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF5B8DEF)
            )
        ) {
            Text(
                text = "Search Weather",
                fontSize = 18.sp
            )
        }

        Spacer(
            modifier = Modifier.height(25.dp)
        )
        if (viewModel.isLoading) {

            CircularProgressIndicator()

            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
        if (weather != null) {

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF30323A)
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = city,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )
                    Text(
                        text = when (weather.current.weather_code) {
                            0 -> "☀️ Clear Sky"
                            1, 2, 3 -> "⛅ Partly Cloudy"
                            45, 48 -> "🌫️ Foggy"
                            51, 53, 55 -> "🌦️ Drizzle"
                            61, 63, 65 -> "🌧️ Rainy"
                            71, 73, 75 -> "❄️ Snowy"
                            80, 81, 82 -> "🌧️ Rain Showers"
                            95, 96, 99 -> "⛈️ Thunderstorm"
                            else -> "🌤️ Unknown"
                        },
                        fontSize = 20.sp,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(12.dp)
                    )
                    Text(
                        text = "🌡️  ${weather.current.temperature_2m} °C",
                        fontSize = 22.sp,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    Text(
                        text = "🌡️ Feels Like: ${weather.current.apparent_temperature} °C",
                        fontSize = 18.sp,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )
                    Text(
                        text = "💧  ${weather.current.relative_humidity_2m} %",
                        fontSize = 20.sp,
                        color = Color.White
                    )

                    Spacer(
                        modifier = Modifier.height(14.dp)
                    )

                    Text(
                        text = "💨  ${weather.current.wind_speed_10m} km/h",
                        fontSize = 20.sp,
                        color = Color.White
                    )
                }
            }
        }
        if (weather != null) {

            Text(
                text = "5 Day Forecast",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111827)
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            LazyColumn {
                items(weather.daily.time.indices.toList()) { index ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp, horizontal = 2.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),

                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text = when (weather.daily.weather_code[index]) {
                                    0 -> "☀️ Clear"
                                    1, 2, 3 -> "⛅ Cloudy"
                                    45, 48 -> "🌫️ Foggy"
                                    51, 53, 55 -> "🌦️ Drizzle"
                                    61, 63, 65 -> "🌧️ Rain"
                                    71, 73, 75 -> "❄️ Snow"
                                    80, 81, 82 -> "🌧️ Showers"
                                    95, 96, 99 -> "⛈️ Storm"
                                    else -> "🌤️"
                                }
                            )

                            Text(
                                text = "${weather.daily.temperature_2m_min[index]}° / " +
                                        "${weather.daily.temperature_2m_max[index]}°"
                            )
                        }
                    }
                }
            }
        }
        if (viewModel.errorMessage != null) {

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = viewModel.errorMessage!!,
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )
        }
    }
}