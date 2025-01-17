package com.trp.open_weather.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherData(
    val coord: Coordinate,
    @SerialName("weather")
    val weathers: List<Weather>,
    val base: String,
    @SerialName("main")
    val tempMain: TempMain,
    val visibility: Int,
    val wind: Wind,
//    val rain: Rain,
    val clouds: Clouds,
    val dt: Long,
//    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

@Serializable
data class Coordinate(val lon: Double, val lat: Double)

@Serializable
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class TempMain(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("sea_level")
    val seaLevel: Int,
    @SerialName("grnd_level")
    val groundLevel: Int
)

@Serializable
data class Wind(
    val speed: Double,
    val deg: Int,
)

@Serializable
data class Rain(
    @SerialName("1h")
    val hourly: Double
)

@Serializable
data class Clouds(
    val all: Int
)

@Serializable
data class Sys(
    val type: Int,
    val id: Int,
)
