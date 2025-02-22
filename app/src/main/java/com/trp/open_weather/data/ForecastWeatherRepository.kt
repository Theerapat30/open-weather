package com.trp.open_weather.data

import com.trp.open_weather.data.di.OpenWeatherApiKey
import com.trp.open_weather.data.remote.open_weather.OpenWeatherApi
import com.trp.open_weather.model.Temp
import com.trp.open_weather.model.tempForecastDummy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface ForecastWeatherRepository {

    suspend fun getWeatherByLocation(latitude: Double, longitude: Double): Result<List<Temp>>

}

class ForecastWeatherOpenWeatherRepository @Inject constructor(
    private val service: OpenWeatherApi,
    @OpenWeatherApiKey private val apiKey: String
) : ForecastWeatherRepository {

    override suspend fun getWeatherByLocation(latitude: Double, longitude: Double): Result<List<Temp>> {
        return withContext(Dispatchers.IO){
            try {
                val forecastData = service.getForecastWeather(
                    latitude = latitude.toString(),
                    longitude = longitude.toString(),
                    key = apiKey
                ).list.map { item ->
                    Temp(
                        temp = item.main.temp,
                        tempFeels = item.main.feelsLike,
                        tempMax = item.main.tempMax,
                        tempMin = item.main.tempMin,
                        dateTime = item.dt
                    )
                }
                Result.Success(forecastData)
            } catch (e: HttpException){
                Result.Error(e)
            }
        }
    }

}

class ForecastFakeWeatherRepository @Inject constructor() : ForecastWeatherRepository {

    override suspend fun getWeatherByLocation(latitude: Double, longitude: Double): Result<List<Temp>> {
        delay(800)

        return withContext(Dispatchers.IO){
            Result.Success(tempForecastDummy)
        }
    }

}