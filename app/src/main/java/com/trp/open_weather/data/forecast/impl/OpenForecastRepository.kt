package com.trp.open_weather.data.forecast.impl

import com.trp.open_weather.MyApplication
import com.trp.open_weather.data.Result
import com.trp.open_weather.data.forecast.ForecastRepository
import com.trp.open_weather.data.remote.OpenWeatherApi
import com.trp.open_weather.model.Temp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class OpenForecastRepository @Inject constructor() : ForecastRepository{

    private val tempList = MutableStateFlow<List<Temp>?>(null)

    override suspend fun getForecastWeatherLocation(latitude: Double, longitude: Double): Result<List<Temp>> {
        val key = MyApplication.API_KEY
        val service = OpenWeatherApi.retrofitService
        val forecastData = service.getForecastWeather(latitude = latitude.toString(), longitude = longitude.toString(), key = key)

        tempList.update {
            forecastData.list.map { item ->
                Temp(
                    temp = item.main.temp,
                    tempFeels = item.main.feelsLike,
                    tempMax = item.main.tempMax,
                    tempMin = item.main.tempMin,
                    dateTime = item.dt
                )
            }
        }

        return Result.Success(tempList.value!!)
    }
}