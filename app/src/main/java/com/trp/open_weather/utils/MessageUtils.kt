package com.trp.open_weather.utils

fun makeWord(data: String): String{
    return data.first().uppercase()+data.substring(1).lowercase()
}