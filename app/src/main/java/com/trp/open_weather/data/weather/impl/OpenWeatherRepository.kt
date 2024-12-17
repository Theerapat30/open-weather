package com.trp.open_weather.data.weather.impl

import com.trp.open_weather.MyApplication
import com.trp.open_weather.data.Result
import com.trp.open_weather.data.airPollutionDummy
import com.trp.open_weather.data.remote.OpenWeatherApi
import com.trp.open_weather.data.remote.OpenWeatherService
import com.trp.open_weather.data.remote.WeatherData
import com.trp.open_weather.data.weather.WeatherRepository
import com.trp.open_weather.model.Clouds
import com.trp.open_weather.model.Temp
import com.trp.open_weather.model.Weather
import com.trp.open_weather.model.Wind
import com.trp.open_weather.utils.utcToDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDateTime
import javax.inject.Inject

class OpenWeatherRepository @Inject constructor() : WeatherRepository {

    private val weather = MutableStateFlow<Weather?>(null)

    override suspend fun getWeather(locationName: String): Result<Weather> {
        val key = MyApplication.API_KEY
        val service: OpenWeatherService = OpenWeatherApi.retrofitService
        val weatherData: WeatherData = service.getWeather(location = locationName, key = key)
        val forecastWeatherData = service.getForecastWeather(location = locationName, key = key)
        val now = LocalDateTime.now()

        weather.update {
            Weather(
                date = utcToDate(weatherData.dt),
                locationName = weatherData.name,
                temp = Temp(
                    temp = weatherData.tempMain.temp,
                    tempFeels = weatherData.tempMain.feelsLike,
                    tempMax = weatherData.tempMain.tempMax,
                    tempMin = weatherData.tempMain.tempMin,
                ),
                mainWeather = if (weatherData.weathers.isNotEmpty()) weatherData.weathers[0].main else "NA",
                weatherDesc = if (weatherData.weathers.isNotEmpty()) weatherData.weathers[0].description else "NA",
                wind = Wind(
                    speed = weatherData.wind.speed,
                    degree = weatherData.wind.deg.toDouble(),
                ),
                clouds = Clouds(
                    all = weatherData.clouds.all.toDouble()
                ),
                tempForecastList = forecastWeatherData.list.map { item ->
                    Temp(
                        temp = item.main.temp,
                        tempFeels = item.main.feelsLike,
                        tempMax = item.main.tempMax,
                        tempMin = item.main.tempMin,
                        dateTime = utcToDate(item.dt)
                    )
                },
                airPollution = airPollutionDummy,
            )
        }
        return Result.Success(weather.value!!)
    }

    override fun observeWeather(): Flow<Weather?> = weather
}