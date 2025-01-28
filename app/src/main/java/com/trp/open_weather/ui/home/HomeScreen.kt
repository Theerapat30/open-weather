package com.trp.open_weather.ui.home

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.trp.open_weather.data.weatherDummy
import com.trp.open_weather.model.Temp
import com.trp.open_weather.model.Weather
import com.trp.open_weather.ui.theme.MyApplicationTheme
import com.trp.open_weather.ui.theme.PrimaryColor
import com.trp.open_weather.ui.theme.PrimaryFontColor
import com.trp.open_weather.ui.theme.SecondaryColor
import com.trp.open_weather.ui.theme.SunnyGradientColor
import com.trp.open_weather.ui.theme.Typography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint(
    "MissingPermission",
    "CoroutineCreationDuringComposition",
    "PermissionLaunchedDuringComposition"
)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToForecastWeather: (Temp) -> Unit,
){
    val context = LocalContext.current

    val locationClient: FusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Log.d("TAG", "HomeScreen uiState...")

    val locationListener: OnSuccessListener<Location> = OnSuccessListener<Location>{ location ->
        viewModel.fetchWeather(latitude = location.latitude, longitude = location.longitude)
    }

    if (uiState is HomeUiState.HasWeather){
        HomeScreen(
            weather = (uiState as HomeUiState.HasWeather).weather,
            onRefresh = {
                locationClient.lastLocation.addOnSuccessListener(locationListener)
            },
            isRefreshing = uiState.isLoading,
            versionName = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_ACTIVITIES
            ).versionName,
            onNavigateToForecastWeather = onNavigateToForecastWeather
        )
    } else {
        locationClient.lastLocation.addOnSuccessListener(locationListener)
        NoWeatherDataScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("MissingPermission")
@Composable
internal fun HomeScreen(
    weather: Weather,
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    versionName: String? = "unknown",
    onNavigateToForecastWeather: (Temp) -> Unit,
){
    val screenHorizontalPadding = 20.dp

    val pullToRefreshState = rememberPullToRefreshState()

    val context = LocalContext.current

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = screenHorizontalPadding, vertical = 32.dp),
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
                    IconButton(onClick = { Toast.makeText(context, "Under construction", Toast.LENGTH_SHORT).show() }) {
                        Icon(Icons.Outlined.Search, contentDescription = null, tint = PrimaryColor)
                    }
                    IconButton(onClick = { Toast.makeText(context, "Under construction", Toast.LENGTH_SHORT).show() }) {
                        Icon(Icons.Outlined.Settings, contentDescription = null, tint = PrimaryColor)
                    }
                }
            }
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "version: $versionName", color = Color.Gray)
            }
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = onRefresh,
            modifier = Modifier.fillMaxSize(),
            state = pullToRefreshState,
            indicator = {
                PullToRefreshDefaults.Indicator(
                    modifier = Modifier.align(Alignment.TopCenter),
                    isRefreshing = isRefreshing,
                    containerColor = PrimaryColor,
                    color = SecondaryColor,
                    state = pullToRefreshState
                )
            }

        ) {
            LazyColumn(
                modifier = Modifier
                    .background(Brush.linearGradient(SunnyGradientColor))
                    .fillMaxSize()
                    .padding(
                        horizontal = screenHorizontalPadding,
                        vertical = innerPadding.calculateTopPadding()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    TempPanel(
                        dateString = weather.dateRepresent,
                        temp = weather.temp
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    TempForecastPanel(
                        modifier = Modifier.fillMaxWidth(),
                        temps = weather.forecastWeathersFromCurrentTime(),
                        onItemSelected = onNavigateToForecastWeather
                    )
                    Spacer(modifier = Modifier.size(20.dp))
                    AirPollutionPanel(modifier = Modifier.fillMaxWidth(), airPollution = weather.airPollution)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoWeatherDataScreen(){
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Loading", style = Typography.headlineMedium)
            LinearProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    val weather = weatherDummy
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val onRefresh: () -> Unit = {
        isRefreshing = true
        coroutineScope.launch {
            delay(1500)
            isRefreshing = false
        }
    }
    MyApplicationTheme {
        HomeScreen(
            weather = weather,
            onRefresh = onRefresh,
            isRefreshing,
            onNavigateToForecastWeather = {item -> }
        )
    }
}