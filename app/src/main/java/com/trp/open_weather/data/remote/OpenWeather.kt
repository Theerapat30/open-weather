package com.trp.open_weather.data.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"

private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

private val client = OkHttpClient.Builder().addInterceptor(interceptor);
private val okHttpClient = client.build()

private val json = Json { ignoreUnknownKeys = true }

private val retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .client(okHttpClient)
    .baseUrl(BASE_URL)
    .build()

interface OpenWeatherService{
    @GET("weather")
    suspend fun getWeather(@Query("q") location: String, @Query("APPID") key: String, @Query("units") units: String = "metric"): WeatherData

    @GET("weather")
    suspend fun getWeather(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("APPID") key: String, @Query("units") units: String = "metric"): WeatherData

    @GET("forecast")
    suspend fun getForecastWeather(@Query("q") location: String, @Query("appid") key: String, @Query("units") units: String = "metric"): ForecastWeatherData

    @GET("forecast")
    suspend fun getForecastWeather(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("appid") key: String, @Query("units") units: String = "metric"): ForecastWeatherData

    @GET("air_pollution")
    suspend fun getCurrentAirPollution(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("appid") key: String): AirPollutionData
}

object OpenWeatherApi{
    val retrofitService: OpenWeatherService by lazy {
        retrofit.create(OpenWeatherService::class.java)
    }
}