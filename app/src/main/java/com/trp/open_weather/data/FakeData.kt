package com.trp.open_weather.data

import com.trp.open_weather.model.AirPollution
import com.trp.open_weather.model.AqiStatus
import com.trp.open_weather.model.Clouds
import com.trp.open_weather.model.Rain
import com.trp.open_weather.model.Temp
import com.trp.open_weather.model.Weather
import com.trp.open_weather.model.Wind
import com.trp.open_weather.utils.utcToDate

val tempDummy = Temp(
    temp = 14.5,
    tempFeels = 15.0,
    tempMax = 18.0,
    tempMin = 12.0
)

val windDummy = Wind(
    speed = 4.59,
    degree = 35.0
)

val rainDummy = Rain(
    hour = 20.0
)

val cloudsDummy = Clouds(
    all = 95.0
)

val airPollutionDummy = AirPollution(
    aqi = AqiStatus.Good,
    carbon = 240.33,
    ozone = 54.36,
    pm25 = 18.03,
    pm10 = 54.9
)

val tempForecastDummy = listOf(
    Temp(
        dateTime = 1734166800L,
        temp = 30.1,
        tempFeels = 29.0,
        tempMax = 32.5,
        tempMin = 28.0
    ),
    Temp(
        dateTime = 1734220800L,
        temp = 25.4,
        tempFeels = 24.5,
        tempMax = 25.6,
        tempMin = 21.9,
    ),
    Temp(
        dateTime = 1734307200L,
        temp = 22.4,
        tempFeels = 22.5,
        tempMax = 23.6,
        tempMin = 20.9,
    ),
    Temp(
        dateTime = 1734393600L,
        temp = 22.93,
        tempFeels = 22.85,
        tempMax = 22.93,
        tempMin = 22.93,
    ),
    Temp(
        dateTime = 1734480000L,
        temp = 24.04,
        tempFeels = 23.99,
        tempMax = 24.04,
        tempMin = 24.04,
    ),
)

val weatherDummy = Weather(
    locationName = "Bangkok",
    temp = tempDummy,
    mainWeather = "",
    weatherDesc = "",
    wind = windDummy,
//    rain = rainDummy,
    clouds = cloudsDummy,
    airPollution = airPollutionDummy,
    tempForecastList = tempForecastDummy
)