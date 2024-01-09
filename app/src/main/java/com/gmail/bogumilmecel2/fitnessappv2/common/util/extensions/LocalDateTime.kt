package com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions

import kotlinx.datetime.DateTimePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

fun LocalDateTime.plusDays(
    days: Int,
    timeZone: TimeZone = TimeZone.UTC
) = this
    .toInstant(timeZone = timeZone)
    .plus(period = DateTimePeriod(days = days), timeZone = timeZone)
    .toLocalDateTime(timeZone = timeZone)