package com.trp.open_weather.data

import com.trp.open_weather.model.Clouds
import com.trp.open_weather.model.Rain
import com.trp.open_weather.model.Temp
import com.trp.open_weather.model.Wind

val dummyWeatherData = Weather(
    lat = -0.1246,
    long = 51.5085,
    weatherMoreInfos = listOf(WeatherMoreInfo(
        groupId = 500,
        groupName = "Rain",
        description = "light rain",
        iconName = "10d"
    )),
    temp = 279.63,
    tempMax = 280.47,
    tempMin = 278.49,
    tempFeelsLike = 275.33,
    windSpeed = 7.72,
    windDegree = 330,
    timezone = 0,
    locationName = "London",
    pressure = 1016,
    humidity = 87
)

val tempDummy = Temp(
    temp = 14.0,
    tempFeels = 15.0,
    maxTemp = 18.0,
    minTemp = 12.0
)

val windDummy = Wind(
    speed = 4.59,
    degree = 35.0
)

val rainDummy = Rain(
    hour = 20.0
)

val clouds = Clouds(
    all = 95.0
)

val weatherDummy = com.trp.open_weather.model.Weather(
    locationName = "Bangkok",
    temp = tempDummy,
    mainWeather = "",
    weatherDesc = "",
    wind = windDummy,
    rain = rainDummy,
    clouds = clouds
)



