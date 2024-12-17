package com.trp.open_weather.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trp.open_weather.data.weatherDummy
import com.trp.open_weather.model.Weather
import com.trp.open_weather.ui.theme.MyApplicationTheme
import com.trp.open_weather.ui.theme.PrimaryColor
import com.trp.open_weather.ui.theme.PrimaryFontColor
import com.trp.open_weather.ui.theme.SunnyGradientColor

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState is HomeUiState.HasWeather){
        HomeScreen(weather = (uiState as HomeUiState.HasWeather).weather)
    }
    else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No Data")
        }
    }
}

@Composable
internal fun HomeScreen(
    weather: Weather
){
    val screenHorizontalPadding = 20.dp
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = screenHorizontalPadding),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        tint = PrimaryColor
                    )
                    Spacer(modifier = Modifier.size(14.dp))
                    Text(weather.locationName, color = PrimaryFontColor, fontWeight = FontWeight.Bold)
                }
                Row {
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Search, contentDescription = null, tint = PrimaryColor)
                    }
                    IconButton(onClick = {}) {
                        Icon(Icons.Outlined.Settings, contentDescription = null, tint = PrimaryColor)
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(Brush.linearGradient(SunnyGradientColor))
                .fillMaxSize()
                .padding(horizontal = screenHorizontalPadding, vertical = innerPadding.calculateTopPadding()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TempPanel(
                dateString = weather.dateRepresent,
                temp = weather.temp
            )
            Spacer(modifier = Modifier.size(20.dp))
            TempForecastPanel(modifier = Modifier.fillMaxWidth(), temps = weather.forecastDailyWeatherFromCurrentTime())
            Spacer(modifier = Modifier.size(20.dp))
            AirPollutionPanel(modifier = Modifier.fillMaxWidth(), airPollution = weather.airPollution)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    val weather = weatherDummy
    MyApplicationTheme {
        HomeScreen(weather = weather)
    }
}