package com.trp.open_weather.ui.forecast

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trp.open_weather.data.Weather
import com.trp.open_weather.data.dummyWeatherData
import com.trp.open_weather.ui.theme.LinearBlue
import com.trp.open_weather.ui.theme.MyApplicationTheme

@Composable
fun ForecastScreen(viewModel: ForecastViewModel = hiltViewModel()){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var openAlertDialog by remember { mutableStateOf(false) }

    if (uiState is ForecastUiState.Success){
        openAlertDialog = false
        ForecastScreen(
            onSearch = viewModel::searchLocation,
            weather = (uiState as ForecastUiState.Success).weather
        )
    }
    else if (uiState is ForecastUiState.Error){
        openAlertDialog = true
    }
    else if (uiState is ForecastUiState.Loading){
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.width(32.dp),
                color = Color.Red,
                trackColor = Color.Yellow,
            )
        }
    }

    if (openAlertDialog){
        AlertDialog(
            onDismissRequest = viewModel::reset,
            text = {
                Text(text = (uiState as ForecastUiState.Error).exception.message ?: "Something went wrong")
            },
            confirmButton = {
                TextButton(onClick = viewModel::reset) {
                    Text(text = "CLOSE")
                }
            }
        )
    }
}

@Composable
internal fun ForecastScreen(
    onSearch: (location: String) -> Unit,
    weather: Weather
){
    var locationName by remember { mutableStateOf("") }

    val scrollableState = rememberScrollState()

    val configuration = LocalConfiguration.current

    if (ORIENTATION_LANDSCAPE != configuration.orientation){
        Column(
            modifier = Modifier
                .background(Brush.verticalGradient(LinearBlue))
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            LocationNameCompose(locationName = weather.locationName)
            TemperatureCompose(
                modifier = Modifier.fillMaxWidth(),
                temp = weather.temp.toString(),
                tempMax = weather.tempMax.toString(),
                tempMin = weather.tempMin.toString()
            )
            WindCompose(
                modifier = Modifier.fillMaxWidth(),
                windSpeed = weather.windSpeed.toString(),
                tempFeel = weather.tempFeelsLike.toString(),
                pressure = weather.pressure.toString(),
                humidity = weather.humidity.toString()
            )
            WeatherCompose(weather = weather, state = scrollableState)
            LocationInputCompose(
                modifier = Modifier.weight(1f, false),
                locationName = locationName,
                onChange = {locationName = it},
                onSearch = onSearch
            )
        }
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(LinearBlue))
                .padding(horizontal = 70.dp),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                LocationNameCompose(modifier = Modifier.fillMaxWidth(0.5f), locationName = weather.locationName)
                LocationInputCompose(
                    locationName = locationName,
                    onChange = {locationName = it},
                    onSearch = onSearch
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TemperatureCompose(
                    modifier = Modifier
                        .fillMaxWidth(0.5f),
                    temp = weather.temp.toString(),
                    tempMax = weather.tempMax.toString(),
                    tempMin = weather.tempMin.toString()
                )
                Spacer(Modifier.size(12.dp))
                WindCompose(
                    modifier = Modifier.fillMaxWidth(1.0f),
                    windSpeed = weather.windSpeed.toString(), tempFeel = weather.tempFeelsLike.toString(),
                    pressure = weather.pressure.toString(), humidity = weather.humidity.toString()
                )
            }
            WeatherCompose(weather = weather, state = scrollableState)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultForecastScreenPreview(){
    val weather = dummyWeatherData

    MyApplicationTheme(){
        ForecastScreen(
            onSearch = {},
            weather = weather
        )
    }
}

@Preview(showBackground = true, widthDp = 1400, heightDp = 800)
@Composable
fun PortraitForecastScreenPreview(){
    val weather = dummyWeatherData

    MyApplicationTheme(){
        ForecastScreen(
            onSearch = {},
            weather = weather
        )
    }
}

