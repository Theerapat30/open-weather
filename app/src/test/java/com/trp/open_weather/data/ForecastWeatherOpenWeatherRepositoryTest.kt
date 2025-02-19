package com.trp.open_weather.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.trp.open_weather.data.remote.open_weather.OpenWeatherApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit

@RunWith(MockitoJUnitRunner::class)
class ForecastWeatherOpenWeatherRepositoryTest {

    private var lat: Double = 0.00
    private var lon: Double = 0.00
    lateinit var key: String

    lateinit var retrofit: Retrofit

    val OPEN_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/"

    val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    val client = OkHttpClient.Builder().addInterceptor(interceptor);
    val okHttpClient = client.build()

    val json = Json { ignoreUnknownKeys = true }

    lateinit var service: OpenWeatherApi

    @Before
    fun setup(){
        lat = 13.717457
        lon = 100.66
        key = "69a4ded70025da0552029cfce527d0c4"

        retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .baseUrl(OPEN_WEATHER_BASE_URL)
            .build()

        service = retrofit.create(OpenWeatherApi::class.java)
    }

    @Test
    fun getWeatherByLocation_correctInput_shouldReturnSuccess() = runTest {
        val repo = ForecastWeatherOpenWeatherRepository(service, key)
        val result = repo.getWeatherByLocation(latitude = lat, longitude = lon)
        Assert.assertTrue(result is Result.Success)
    }

    @Test
    fun getWeatherByLocation_invalidKey_shouldReturnFailed() = runTest {
        val invalidKey = "1234"

        val repo = ForecastWeatherOpenWeatherRepository(service, invalidKey)
        val result = repo.getWeatherByLocation(latitude = lat, longitude = lon)
        Assert.assertTrue(result is Result.Error)
        System.out.println("GOT: "+(result as Result.Error).exception.message)
    }

    @Test
    fun getWeatherByLocation_invalidUrl_shouldReturnFailed() = runTest {
        val invalidUrl = "http://api.openweathermap.org/data/x.x/"
        retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .baseUrl(invalidUrl)
            .build()

        val service = retrofit.create(OpenWeatherApi::class.java)

        val repo = ForecastWeatherOpenWeatherRepository(service, key)
        val result = repo.getWeatherByLocation(latitude = lat, longitude = lon)
        Assert.assertTrue(result is Result.Error)
        System.out.println("GOT: "+(result as Result.Error).exception.message)
    }

}