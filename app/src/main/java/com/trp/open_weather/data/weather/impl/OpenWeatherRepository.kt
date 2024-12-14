package com.trp.open_weather.data.weather.impl

import com.trp.open_weather.MyApplication
import com.trp.open_weather.data.Result
import com.trp.open_weather.data.remote.OpenWeatherApi
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
import javax.inject.Inject

class OpenWeatherRepository @Inject constructor() : WeatherRepository {

    private val weather = MutableStateFlow<Weather?>(null)

    override suspend fun getWeather(locationName: String): Result<Weather> {
        val key = MyApplication.API_KEY
        val result: WeatherData = OpenWeatherApi
            .retrofitService
            .getWeather(location = locationName, key = key)
        weather.update {
            Weather(
                date = utcToDate(result.dt),
                locationName = result.name,
                temp = Temp(
                    temp = result.tempMain.temp,
                    tempFeels = result.tempMain.feelsLike,
                    tempMax = result.tempMain.tempMax,
                    tempMin = result.tempMain.tempMin,
                ),
                mainWeather = if (result.weathers.isNotEmpty()) result.weathers[0].main else "NA",
                weatherDesc = if (result.weathers.isNotEmpty()) result.weathers[0].description else "NA",
                wind = Wind(
                    speed = result.wind.speed,
                    degree = result.wind.deg.toDouble(),
                ),
                clouds = Clouds(
                    all = result.clouds.all.toDouble()
                )

            )
        }
        return Result.Success(weather.value!!)
    }

    override fun observeWeather(): Flow<Weather?> = weather
}