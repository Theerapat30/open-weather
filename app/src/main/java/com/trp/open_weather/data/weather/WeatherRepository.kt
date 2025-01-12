package com.trp.open_weather.data.weather

import com.trp.open_weather.data.Result
import com.trp.open_weather.model.Weather
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton

@Singleton
interface WeatherRepository{

    suspend fun getWeather(locationName: String): Result<Weather>
    suspend fun getWeatherLocation(latitude: Double, longitude: Double): Result<Weather>

    fun observeWeather() : Flow<Weather?>

}