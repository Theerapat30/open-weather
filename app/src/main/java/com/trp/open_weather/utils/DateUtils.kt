package com.trp.open_weather.utils

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

fun utcToDate(dateUtc: Long): LocalDateTime{
    return LocalDateTime.ofEpochSecond(dateUtc, 0, ZoneOffset.UTC)
}

fun dateToUtc(date: LocalDateTime): Long{
    return date.toEpochSecond(ZoneOffset.UTC)
}

fun isTimeAfterHour(hour: Long, time: LocalTime, specificTime: LocalTime): Boolean{
    return time.isBefore(specificTime) && time.plusHours(hour).isAfter(specificTime)
}