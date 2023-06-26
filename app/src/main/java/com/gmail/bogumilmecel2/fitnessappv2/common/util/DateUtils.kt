package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.text.format.DateUtils
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

object DateUtils {

    const val US_DATE_PATTERN = "MM/dd/yyyy"
    const val EU_DATE_PATTERN = "dd/MM/yyyy"

    fun getCurrentTimestamp() = getCurrentLocalDateTime().toInstant(UtcOffset.ZERO).toEpochMilliseconds()

    fun getCurrentDateString() = getCurrentLocalDateTime().date.toString()

    private fun getCurrentLocalDateTime() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    fun getCurrentTimezoneId() = TimeZone.currentSystemDefault().id

    fun Long.getLocalDateString() = getLocalDateTime().date.toString()

    fun Long.isTomorrow() = DateUtils.isToday(this - DateUtils.DAY_IN_MILLIS)

    fun Long.isYesterday() = DateUtils.isToday(this + DateUtils.DAY_IN_MILLIS)

    fun Long.isToday() = DateUtils.isToday(this)

    private fun Long.getLocalDateTime() = Instant.fromEpochMilliseconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}