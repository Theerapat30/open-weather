package com.trp.open_weather.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.currentStateAsState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.trp.open_weather.data.weatherDummy
import com.trp.open_weather.model.Weather
import com.trp.open_weather.ui.theme.MyApplicationTheme
import com.trp.open_weather.ui.theme.PrimaryColor
import com.trp.open_weather.ui.theme.PrimaryFontColor
import com.trp.open_weather.ui.theme.SunnyGradientColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission", "CoroutineCreationDuringComposition",
    "PermissionLaunchedDuringComposition"
)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
){

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.RequestMultiplePermissions(),
//        onResult = { permissions ->
//            val permissionGranted = permissions.values.reduce { acc, isPermission ->  acc && isPermission }
//            if (!permissionGranted){
//
//            }
//        }
//    )

    val context = LocalContext.current
    val locationClient: FusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    val currentLifecycleState: State<Lifecycle.State> = lifecycleOwner.lifecycle.currentStateAsState()
    if (currentLifecycleState.value == Lifecycle.State.STARTED){
        Log.d("TAG", "HomeScreen: STARTED")
    } else if (currentLifecycleState.value == Lifecycle.State.CREATED){
        Log.d("TAG", "HomeScreen: CREATED")
    } else if (currentLifecycleState.value == Lifecycle.State.RESUMED){
        Log.d("TAG", "HomeScreen: RESUMED")
        if (!locationPermissionsState.allPermissionsGranted){
            locationPermissionsState.launchMultiplePermissionRequest()
        }
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Log.d("TAG", "HomeScreen uiState...")

    if (uiState is HomeUiState.HasWeather){
        HomeScreen(weather = (uiState as HomeUiState.HasWeather).weather)
    } else {
        Log.d("TAG", "last location listening...")
        locationClient.lastLocation.addOnSuccessListener { location ->
            Log.d("TAG", "Got location")
            viewModel.fetchWeather(latitude = location.latitude, longitude = location.longitude)
        }
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No Data")
        }
    }
}

@SuppressLint("MissingPermission")
@Composable
internal fun HomeScreen(
    weather: Weather,
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
                .padding(
                    horizontal = screenHorizontalPadding,
                    vertical = innerPadding.calculateTopPadding()
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TempPanel(
                dateString = weather.dateRepresent,
                temp = weather.temp
            )
            Spacer(modifier = Modifier.size(20.dp))
            TempForecastPanel(modifier = Modifier.fillMaxWidth(), temps = weather.forecastWeathersFromCurrentTime())
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