package com.trp.open_weather.data.remote

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class WeatherRemoteDataSourceTest {

    lateinit var lat: String
    lateinit var lon: String
    lateinit var key: String

    @Before
    fun setup(){
        lat = "13.717457"
        lon = "100.66"
        key = "69a4ded70025da0552029cfce527d0c4"
    }

//    @Test
//    fun getCurrentWeather_correctInput_shouldReturnData() = runTest {
//        val service = WeatherRemoteDataSource.openWeatherService
//        val model = service.getCurrentWeather(key = key, latitude = lat, longitude = lon)
//        println(model)
//        Assert.assertNotNull(model)
//    }
//
//    @Test
//    fun getCurrentWeather_invalidKey_shouldReturn401() = runTest {
//        val service = WeatherRemoteDataSource.openWeatherService
//        val invalidKey = "abcdefg"
//        try {
//            service.getCurrentWeather(key = invalidKey, latitude = lat, longitude = lon)
//        } catch (e: HttpException){
//            Assert.assertEquals(401, e.code())
//        }
//    }
//
//    @Test
//    fun getForecastWeather_correctInput_shouldReturnData() = runTest {
//        val service = WeatherRemoteDataSource.openWeatherService
//        val model = service.getCurrentWeather(key = key, latitude = lat, longitude = lon)
//        println(model)
//        Assert.assertNotNull(model)
//    }

}