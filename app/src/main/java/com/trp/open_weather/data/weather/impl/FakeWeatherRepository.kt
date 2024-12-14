package com.trp.open_weather.data.weather.impl

import com.trp.open_weather.data.Result
import com.trp.open_weather.data.weather.WeatherRepository
import com.trp.open_weather.data.weatherDummy
import com.trp.open_weather.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeWeatherRepository @Inject constructor(): WeatherRepository {

    private val weather = MutableStateFlow<Weather?>(null)

    override suspend fun getWeather(locationName: String): Result<Weather> {
        delay(800)
        val fakeWeather = weatherDummy
        weather.update { fakeWeather }
        return withContext(Dispatchers.IO){
            Result.Success(fakeWeather)
        }
    }

    override fun observeWeather(): Flow<Weather?> = weather
}