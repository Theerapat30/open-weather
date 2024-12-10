package com.trp.open_weather.data

import com.trp.open_weather.data.remote.OpenWeatherApi
import com.trp.open_weather.data.remote.WeatherData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

data class Weather(
    val lat: Double = 0.0,
    val long: Double = 0.0,
    val weatherMoreInfos: List<WeatherMoreInfo> = emptyList(),
    val temp: Double = 0.0,
    val tempFeelsLike: Double = 0.0,
    val tempMax: Double = 0.0,
    val tempMin: Double = 0.0,
    val windSpeed: Double = 0.0,
    val windDegree: Int = 0,
    val timezone: Int = 0,
    val locationName: String = "",
    val pressure: Int = 0,
    val humidity: Int = 0
)

data class WeatherMoreInfo(
    val groupId: Int,
    val groupName: String,
    val description: String,
    val iconName: String,
)

interface ForecastRepository {
    suspend fun weathers(location: String) : Flow<Weather>
}

class OpenWeatherRepository @Inject constructor() : ForecastRepository{

    override suspend fun weathers(location: String): Flow<Weather> {
        return flow {
            val weatherData: WeatherData = OpenWeatherApi
                .retrofitService
                .getWeather(location = location, key = "69a4ded70025da0552029cfce527d0c4")
            emit(
                Weather(
                    lat = weatherData.coord.lat,
                    long = weatherData.coord.lon,
                    weatherMoreInfos = if (weatherData.weathers.isNotEmpty()) weatherData.weathers.map {
                        WeatherMoreInfo(
                            groupId = it.id,
                            groupName = it.main,
                            description = it.description,
                            iconName = it.icon,
                        )
                    } else {
                        emptyList()
                    },
                    temp = weatherData.tempMain.temp,
                    tempFeelsLike = weatherData.tempMain.feelsLike,
                    tempMax = weatherData.tempMain.tempMax,
                    tempMin = weatherData.tempMain.tempMin,
                    windSpeed = weatherData.wind.speed,
                    windDegree = weatherData.wind.deg,
                    timezone = weatherData.timezone,
                    locationName = weatherData.name,
                    pressure = weatherData.tempMain.pressure,
                    humidity = weatherData.tempMain.humidity
                )
            )
        }
    }

}