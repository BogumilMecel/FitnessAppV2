package com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

fun LocalDate.minusDays(days: Int) = this.minus(DatePeriod(days = days))
fun LocalDate.plusDays(days: Int) = this.plus(DatePeriod(days = days))
fun LocalDate.getDisplayDate(resourceProvider: ResourceProvider): String {
    val currentDate = CustomDateUtils.getDate()

    if (this == currentDate) return resourceProvider.getString(R.string.today)
    if (this == currentDate.minusDays(1)) return resourceProvider.getString(R.string.yesterday)
    if (this == currentDate.plusDays(1)) return resourceProvider.getString(R.string.tomorrow)
    return this.toString()
}