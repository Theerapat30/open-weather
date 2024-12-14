package com.trp.open_weather.data

import com.trp.open_weather.model.Clouds
import com.trp.open_weather.model.Rain
import com.trp.open_weather.model.Temp
import com.trp.open_weather.model.Wind

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

val clouds = Clouds(
    all = 95.0
)

val weatherDummy = com.trp.open_weather.model.Weather(
    locationName = "Bangkok",
    temp = tempDummy,
    mainWeather = "",
    weatherDesc = "",
    wind = windDummy,
//    rain = rainDummy,
    clouds = clouds
)