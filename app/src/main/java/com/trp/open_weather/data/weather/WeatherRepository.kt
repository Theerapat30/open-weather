package com.trp.open_weather.data.weather

import com.trp.open_weather.data.Result
import com.trp.open_weather.model.Weather
import kotlinx.coroutines.flow.Flow

interface WeatherRepository{

    suspend fun getWeather(locationName: String): Result<Weather>

    fun observeWeather() : Flow<Weather?>

}