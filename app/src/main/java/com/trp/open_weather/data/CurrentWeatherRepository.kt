package com.trp.open_weather.data

import com.trp.open_weather.data.di.OpenWeatherApiKey
import com.trp.open_weather.data.remote.open_weather.OpenWeatherApi
import com.trp.open_weather.data.remote.open_weather.models.AirPollutionApiModel
import com.trp.open_weather.data.remote.open_weather.models.AirPollutionListItemApiModel
import com.trp.open_weather.data.remote.open_weather.models.WeatherApiModel
import com.trp.open_weather.model.AirPollution
import com.trp.open_weather.model.AqiStatus
import com.trp.open_weather.model.Clouds
import com.trp.open_weather.model.Temp
import com.trp.open_weather.model.Weather
import com.trp.open_weather.model.Wind
import com.trp.open_weather.model.weatherDummy
import com.trp.open_weather.utils.utcToDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
interface CurrentWeatherRepository{

    suspend fun getWeather(locationName: String): Result<Weather>
    suspend fun getWeatherLocation(latitude: Double, longitude: Double): Result<Weather>

    fun observeWeather() : Flow<Weather?>

}

class CurrentWeatherOpenWeatherRepository @Inject constructor(
    private val service: OpenWeatherApi,
    @OpenWeatherApiKey private val key: String
) : CurrentWeatherRepository {

    private val weather = MutableStateFlow<Weather?>(null)

    override suspend fun getWeather(locationName: String): Result<Weather> {
//        val service: OpenWeatherApi = WeatherRemoteDataSource.openWeatherService
        val weatherApiModel: WeatherApiModel = service.getCurrentWeather(location = locationName, key = key)
        val forecastWeatherData = service.getForecastWeather(location = locationName, key = key)
        val airPollutionApiModel: AirPollutionApiModel = service.getCurrentAirPollution(latitude = "50", longitude = "50", key = key)
        val airPollutionItem: AirPollutionListItemApiModel = airPollutionApiModel.list[0]

        weather.update {
            Weather(
                date = utcToDate(weatherApiModel.dt),
                locationName = weatherApiModel.name,
                temp = Temp(
                    temp = weatherApiModel.tempMainApiModel.temp,
                    tempFeels = weatherApiModel.tempMainApiModel.feelsLike,
                    tempMax = weatherApiModel.tempMainApiModel.tempMax,
                    tempMin = weatherApiModel.tempMainApiModel.tempMin,
                ),
                mainWeather = if (weatherApiModel.weatherItemApiModels.isNotEmpty()) weatherApiModel.weatherItemApiModels[0].main else "NA",
                weatherDesc = if (weatherApiModel.weatherItemApiModels.isNotEmpty()) weatherApiModel.weatherItemApiModels[0].description else "NA",
                wind = Wind(
                    speed = weatherApiModel.windApiModel.speed,
                    degree = weatherApiModel.windApiModel.deg.toDouble(),
                ),
                clouds = Clouds(
                    all = weatherApiModel.cloudsApiModel.all.toDouble()
                ),
                tempForecastList = forecastWeatherData.list.map { item ->
                    Temp(
                        temp = item.main.temp,
                        tempFeels = item.main.feelsLike,
                        tempMax = item.main.tempMax,
                        tempMin = item.main.tempMin,
                        dateTime = item.dt
                    )
                },
                airPollution = AirPollution(
                    aqi = toAqiStatus(airPollutionItem.main.aqi),
                    carbon = airPollutionItem.components.co,
                    ozone = airPollutionItem.components.o3,
                    pm25 = airPollutionItem.components.pm25,
                    pm10 = airPollutionItem.components.pm10
                ),
            )
        }
        return Result.Success(weather.value!!)
    }

    override suspend fun getWeatherLocation(latitude: Double, longitude: Double): Result<Weather> {
//        val service: OpenWeatherApi = WeatherRemoteDataSource.openWeatherService
        val weatherApiModel: WeatherApiModel = service.getCurrentWeather(latitude = latitude.toString(), longitude = longitude.toString(), key = key)
        val forecastWeatherData = service.getForecastWeather(latitude = latitude.toString(), longitude = longitude.toString(), key = key)
        val airPollutionApiModel: AirPollutionApiModel = service.getCurrentAirPollution(latitude = latitude.toString(), longitude = longitude.toString(), key = key)
        val airPollutionItem: AirPollutionListItemApiModel = airPollutionApiModel.list[0]

        weather.update {
            Weather(
                date = utcToDate(weatherApiModel.dt),
                locationName = weatherApiModel.name,
                temp = Temp(
                    temp = weatherApiModel.tempMainApiModel.temp,
                    tempFeels = weatherApiModel.tempMainApiModel.feelsLike,
                    tempMax = weatherApiModel.tempMainApiModel.tempMax,
                    tempMin = weatherApiModel.tempMainApiModel.tempMin,
                ),
                mainWeather = if (weatherApiModel.weatherItemApiModels.isNotEmpty()) weatherApiModel.weatherItemApiModels[0].main else "NA",
                weatherDesc = if (weatherApiModel.weatherItemApiModels.isNotEmpty()) weatherApiModel.weatherItemApiModels[0].description else "NA",
                wind = Wind(
                    speed = weatherApiModel.windApiModel.speed,
                    degree = weatherApiModel.windApiModel.deg.toDouble(),
                ),
                clouds = Clouds(
                    all = weatherApiModel.cloudsApiModel.all.toDouble()
                ),
                tempForecastList = forecastWeatherData.list.map { item ->
                    Temp(
                        temp = item.main.temp,
                        tempFeels = item.main.feelsLike,
                        tempMax = item.main.tempMax,
                        tempMin = item.main.tempMin,
                        dateTime = item.dt
                    )
                },
                airPollution = AirPollution(
                    aqi = toAqiStatus(airPollutionItem.main.aqi),
                    carbon = airPollutionItem.components.co,
                    ozone = airPollutionItem.components.o3,
                    pm25 = airPollutionItem.components.pm25,
                    pm10 = airPollutionItem.components.pm10
                ),
            )
        }
        return Result.Success(weather.value!!)
    }

    override fun observeWeather(): Flow<Weather?> = weather

    private fun toAqiStatus(aqi: Int) : AqiStatus {
        return when(aqi){
            1 -> AqiStatus.Good
            2 -> AqiStatus.Fair
            3 -> AqiStatus.Moderate
            4 -> AqiStatus.Poor
            5 -> AqiStatus.VeryPoor
            else -> AqiStatus.NA
        }
    }
}

class CurrentWeatherFakeRepository @Inject constructor() : CurrentWeatherRepository {

    private val weather = MutableStateFlow<Weather?>(null)

    override suspend fun getWeather(locationName: String): Result<Weather> {
        delay(800)
        val fakeWeather = weatherDummy
        weather.update { fakeWeather }
        return withContext(Dispatchers.IO){
            Result.Success(fakeWeather)
        }
    }

    override suspend fun getWeatherLocation(latitude: Double, longitude: Double): Result<Weather> {
        delay(800)
        val fakeWeather = weatherDummy
        weather.update { fakeWeather }
        return withContext(Dispatchers.IO){
            Result.Success(fakeWeather)
        }
    }

    override fun observeWeather(): Flow<Weather?> = weather
}