package com.trp.open_weather.model

import java.util.Date

data class Weather(
    val locationName: String,
    val temp: Temp,
    val mainWeather: String,
    val weatherDesc: String,
    val wind: Wind,
    val rain: Rain,
    val clouds: Clouds,
)

data class Temp(
    val temp: Double,
    val tempFeels: Double,
    val maxTemp: Double,
    val minTemp: Double,
)

data class ForecastTemp(
    val dateTime: Date,
    val day: Double,
    val dayFeelsLike: Double?,
    val night: Double,
    val nightFeelsLike: Double?,
    val evening: Double,
    val eveningFeelsLike: Double?,
    val morning: Double,
    val morningFeelsLike: Double?,
    val minTemp: Double,
    val maxTemp: Double
)

data class AirPollution(
    val aqi: Int,
    val carbon: Double,
    val ozone: Double,
    val pm25: Double,
    val pm10: Double
)

data class Wind(
    val speed: Double,
    val degree: Double
)

data class Rain(
    val hour: Double
)

data class Clouds(
    val all: Double
)

enum class AqiStatus{
    Good,
    Fair,
    Moderate,
    Poor,
    VeryPoor
}

enum class MeasurementUnits{
    Kelvin,
    Celsius,
    Fahrenheit
}