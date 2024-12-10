package com.trp.open_weather.data

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