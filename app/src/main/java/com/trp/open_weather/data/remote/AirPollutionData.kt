package com.trp.open_weather.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollutionData(
    val coord: Coordinate,
    val list: List<AirPollutionListItem>,
)

@Serializable
data class AirPollutionListItem(
    val main: AirPollutionMain,
    val components: AirPollutionComponent,
    val dt: Long
)

@Serializable
data class AirPollutionMain(
    val aqi: Int,
)

@Serializable
data class AirPollutionComponent(
    val co: Double,
    val no: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    @SerialName("pm2_5")
    val pm25: Double,
    val pm10: Double,
    val nh3: Double,
)

