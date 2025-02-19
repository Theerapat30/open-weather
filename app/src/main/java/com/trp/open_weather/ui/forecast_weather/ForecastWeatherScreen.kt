package com.trp.open_weather.ui.forecast_weather

import android.annotation.SuppressLint
import android.location.Location
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.trp.open_weather.R
import com.trp.open_weather.model.tempForecastDummy
import com.trp.open_weather.model.Temp
import com.trp.open_weather.ui.composes.ForecastItem
import com.trp.open_weather.ui.theme.MyApplicationTheme
import com.trp.open_weather.ui.theme.PrimaryColor
import com.trp.open_weather.ui.theme.PrimaryFontColor
import com.trp.open_weather.ui.theme.SunnyGradientColor
import com.trp.open_weather.ui.theme.Typography

@SuppressLint("MissingPermission")
@Composable
fun ForecastWeatherScreen(
    viewModel: ForecastWeatherViewModel = hiltViewModel(),
    onBackStack: () -> Unit,
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val locationClient: FusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val locationListener: OnSuccessListener<Location> = OnSuccessListener<Location>{ location ->
        viewModel.fetchForecastWeather(latitude = location.latitude, longitude = location.longitude)
    }

    if (uiState.items == null){
        locationClient.lastLocation.addOnSuccessListener(locationListener)
        ForecastWeatherLoading(onBackStack = onBackStack)
        return
    }

    if (uiState.isLoading){
        ForecastWeatherLoading(onBackStack = onBackStack)
    } else {
        ForecastWeatherScreen(
            onBackStack = onBackStack,
            titleName = viewModel.dateString,
            temps = uiState.items!!
        )
    }

    uiState.userMessage?.let { message ->
        LaunchedEffect(message) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.userMessageShow()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ForecastWeatherScreen(
    onBackStack: () -> Unit,
    titleName: String,
    temps: List<Temp>
){
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBackStack) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = PrimaryColor)
                }
                Text(text = titleName, color = PrimaryFontColor, fontWeight = FontWeight.Bold)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(SunnyGradientColor))
                .padding(vertical = innerPadding.calculateTopPadding(), horizontal = 16.dp)
        ) {
            items(count = temps.size) { index ->
                val temp = temps[index]
                ForecastItem(
                    modifier = Modifier.padding(bottom = 8.dp),
                    time = temp.time(),
                    iconId = R.drawable.ic_pressure_24,
                    tempInfo = temp.tempDisplay
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastWeatherLoading(onBackStack: () -> Unit){
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackStack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                title = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Loading", style = Typography.headlineMedium)
            LinearProgressIndicator()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ForecastWeatherScreenPreview(){
    val items = tempForecastDummy
    MyApplicationTheme {
        ForecastWeatherScreen(onBackStack = {}, titleName = "28 January", temps = items)
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastWeatherLoadingPreview(){
    MyApplicationTheme {
        ForecastWeatherLoading(onBackStack = {})
    }
}
