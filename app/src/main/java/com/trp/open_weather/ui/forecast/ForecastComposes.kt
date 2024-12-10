package com.trp.open_weather.ui.forecast

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.trp.open_weather.R
import com.trp.open_weather.data.Weather
import com.trp.open_weather.data.dummyWeatherData
import com.trp.open_weather.ui.composes.WeatherInfoItem
import com.trp.open_weather.ui.theme.MyApplicationTheme
import com.trp.open_weather.ui.theme.TextDark
import com.trp.open_weather.ui.theme.Typography
import com.trp.open_weather.ui.theme.fontFamilySelected

@Composable
fun LocationInputCompose(
    modifier: Modifier = Modifier,
    locationName: String,
    onChange: (String) -> Unit,
    onSearch: (String) -> Unit
){
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(0.7f),
            value = locationName,
            onValueChange = onChange,
            singleLine = true,
            label = { Text("Location Name") }
        )
        Button (
            onClick = { onSearch(locationName) },
            colors = ButtonDefaults.buttonColors(contentColor = Color.White, containerColor = TextDark)
        ) {
            Icon(Icons.Filled.Search, contentDescription = null)
        }
    }
}

@Composable
fun LocationNameCompose(modifier: Modifier = Modifier, locationName: String){
    Row(modifier.fillMaxWidth()) {
        Icon(Icons.Outlined.LocationOn, contentDescription = null)
        Spacer(modifier = Modifier.size(8.dp))
        Text(locationName)
    }

}

@Composable
fun TemperatureCompose(modifier: Modifier = Modifier, temp: String, tempMax: String, tempMin: String){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent.copy(alpha = 0.1f)
        )
    ) {
        Column (
            modifier = modifier
                .height(150.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                temp,
                style = TextStyle(
                    fontFamily = fontFamilySelected,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
                    letterSpacing = 2.sp,
                ),
            )
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                horizontalArrangement = Arrangement.Center
            ) {
                Row {
                    Text(tempMax, style = Typography.headlineMedium)
                    Icon(painter = painterResource(R.drawable.ic_thermometer_add_24), contentDescription = null)
                }
                Spacer(modifier = Modifier.size(24.dp))
                Row {
                    Text(tempMin, style = Typography.headlineMedium)
                    Icon(painter = painterResource(R.drawable.ic_thermometer_minus_24), contentDescription = null)
                }
            }
        }
    }

}

@Composable
fun WindCompose(
    modifier: Modifier = Modifier,
    windSpeed: String,
    tempFeel: String,
    pressure: String,
    humidity: String
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent.copy(alpha = 0.1f)
        )
    ) {
        Column (
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherInfoItem(label = "Wind", info = windSpeed, iconId = R.drawable.ic_wind_24)
            WeatherInfoItem(label = "Feel", info = tempFeel, iconId = R.drawable.ic_temp_feels_24)
            WeatherInfoItem(label = "Pres", info = pressure, iconId = R.drawable.ic_pressure_24)
            WeatherInfoItem(label = "Humi", info = humidity, iconId = R.drawable.ic_humidity_24)
        }
    }

}

@Composable
fun WeatherCompose(modifier: Modifier = Modifier, weather: Weather, state: ScrollState){
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        weather.weatherMoreInfos.forEach { info ->
            Column(
                modifier = Modifier
                    .wrapContentHeight()
                    .verticalScroll(state),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Text(info.groupName, style = Typography.headlineLarge)
                Text(info.description, style = Typography.bodyLarge)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationInputComposePreview(){
    MyApplicationTheme {
        LocationInputCompose(locationName = "London", onChange = {}, onSearch = {})
    }
}

@Preview(showBackground = true)
@Composable
fun LocationNameComposePreview(){
    MyApplicationTheme {
        LocationNameCompose(locationName = "London")
    }
}

@Preview(showBackground = true)
@Composable
fun TemperatureComposePreview(){
    MyApplicationTheme {
        TemperatureCompose(temp = "19.0", tempMax = "25.0", tempMin = "18.4")
    }
}

@Preview(showBackground = true)
@Composable
fun WindComposePreview(){
    MyApplicationTheme {
        WindCompose(windSpeed = "7.72", tempFeel = "20.0", pressure = "1016", humidity = "19")
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherComposePreview(){
    val data = dummyWeatherData
    val scrollableState = rememberScrollState()
    MyApplicationTheme {
        WeatherCompose(weather = data, state = scrollableState)
    }
}
