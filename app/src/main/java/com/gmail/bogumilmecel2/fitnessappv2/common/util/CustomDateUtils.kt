package com.gmail.bogumilmecel2.fitnessappv2.common.util

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

object CustomDateUtils {
    fun getDate() = getDateTime().date
    private fun getDateTime() = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
}