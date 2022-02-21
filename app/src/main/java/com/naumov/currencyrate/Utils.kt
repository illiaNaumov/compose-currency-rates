package com.naumov.currencyrate

import java.text.SimpleDateFormat
import java.util.*

private const val SERVER_FORMAT = "yyyy-MM-dd"
private const val UI_FORMAT = "LLL"
private const val MONTHS = 12

fun formatToServerTime(timestamp: Long): String = timestamp.formatAs(SERVER_FORMAT)

fun parseMonth(timestamp: Long): String = timestamp.formatAs(UI_FORMAT)

fun firstDayOfMonths(year: Int): List<Long> {
        val calendar = Calendar.getInstance()
        calendar.clear()
        calendar.set(year, Calendar.JANUARY, 1, 10, 0)

        val dates = mutableListOf<Long>()
        for (month in 0 until MONTHS) {
            calendar.set(year, month, 1)
            dates.add(calendar.timeInMillis)
        }

        return dates
}

private fun Long.formatAs(format: String): String {
    val timeStamp = this
    return SimpleDateFormat(format, Locale.getDefault()).format(Date(timeStamp))
}