package com.trp.open_weather.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneOffset

fun utcToDate(dateUtc: Long): LocalDateTime{
    return LocalDateTime.ofEpochSecond(dateUtc, 0, ZoneOffset.UTC)
}