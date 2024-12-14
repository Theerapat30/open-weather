package com.trp.open_weather.utils

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date

class DateUtilsTest {

    @Test
    fun dateFromUtc_shouldReturnDate() = runTest {
        val dateUtc: Long = 1733984316L

        val date = utcToDate(dateUtc)
        val dayOfWeek = date.dayOfWeek
        val dayOfMonth = date.dayOfMonth
        val month = date.month
        val year = date.year

        System.out.println("dayOfWeek=$dayOfWeek")
        System.out.println("dayOfMonth=$dayOfMonth")
        System.out.println("month=$month")
        System.out.println("year=$year")
    }

    @Test
    fun test() = runTest {
        val date = LocalDateTime.now()
        val dayOfWeek = date.dayOfWeek.name
        System.out.println("${dayOfWeek.first().uppercase()}${dayOfWeek.substring(1).lowercase()}")
        System.out.println("${dayOfWeek.uppercase().substring(1).lowercase()}")
    }

}