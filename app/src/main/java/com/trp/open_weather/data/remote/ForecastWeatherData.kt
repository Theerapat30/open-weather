package com.trp.open_weather.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastWeatherData(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ForecastListItemData>,
    val city: City
)

@Serializable
data class ForecastListItemData(
    val dt: Long,
    val main: TempMain,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    @SerialName("dt_txt")
    val dtText: String,
)

@Serializable
data class City(
    val id: Int,
    val name: String,
    val coord: Coordinate,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
)