package com.trp.open_weather.data.forecast.impl

import com.trp.open_weather.data.forecast.ForecastRepository
import com.trp.open_weather.data.Result
import com.trp.open_weather.data.tempDummy
import com.trp.open_weather.data.tempForecastDummy
import com.trp.open_weather.data.weatherDummy
import com.trp.open_weather.model.Temp
import com.trp.open_weather.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeForecastRepository @Inject constructor() : ForecastRepository {

    private val tempList = MutableStateFlow<List<Temp>?>(null)

    override suspend fun getForecastWeatherLocation(latitude: Double, longitude: Double): Result<List<Temp>> {
        delay(800)

        tempList.update {
            tempForecastDummy
        }

        return withContext(Dispatchers.IO){
            Result.Success(tempList.value!!)
        }
    }
}