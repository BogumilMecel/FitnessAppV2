package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.text.format.DateUtils.DAY_IN_MILLIS
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.DateHolder
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils.getLocalDateString
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils.isToday
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils.isTomorrow
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils.isYesterday
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RealDateHolder : DateHolder {

    private var _currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            .toInstant(UtcOffset.ZERO).toEpochMilliseconds()

    override fun getDateString(resourceProvider: ResourceProvider): String {
        with(_currentDate) {
            return when {
                isToday() -> {
                    resourceProvider.getString(R.string.today)
                }

                isYesterday() -> {
                    resourceProvider.getString(R.string.yesterday)
                }

                isTomorrow() -> {
                    resourceProvider.getString(R.string.tomorrow)
                }

                else -> {
                    Locale.getDefault().getCorrectFormat().format(
                        Date(_currentDate)
                    )
                }
            }
        }
    }

    override fun getLocalDateString() = _currentDate.getLocalDateString()

    override fun addDay() {
        _currentDate += DAY_IN_MILLIS
    }

    override fun deductDay() {
        _currentDate -= DAY_IN_MILLIS
    }

    private fun Locale.getCorrectFormat(): SimpleDateFormat {
        val pattern = when (country) {
            "US" -> CustomDateUtils.US_DATE_PATTERN
            else -> CustomDateUtils.EU_DATE_PATTERN
        }

        return SimpleDateFormat(
            pattern,
            this
        )
    }
}