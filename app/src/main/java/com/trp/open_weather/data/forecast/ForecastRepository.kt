package com.trp.open_weather.data.forecast

import com.trp.open_weather.data.Result
import com.trp.open_weather.model.Temp
import com.trp.open_weather.model.Weather
import javax.inject.Singleton

@Singleton
interface ForecastRepository {

    suspend fun getForecastWeatherLocation(latitude: Double, longitude: Double): Result<List<Temp>>

}