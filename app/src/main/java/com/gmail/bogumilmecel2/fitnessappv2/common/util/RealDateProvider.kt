package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.text.format.DateUtils
import android.text.format.DateUtils.DAY_IN_MILLIS
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.DateProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RealDateProvider : DateProvider {

    companion object {
        private const val US_DATE_PATTERN = "MM/dd/yyyy"
        private const val EU_DATE_PATTERN = "dd/MM/yyyy"
    }

    private val _currentDate: MutableStateFlow<Long> = MutableStateFlow(
        Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).toInstant(UtcOffset.ZERO).toEpochMilliseconds()
    )
    override val currentDate: StateFlow<Long> = _currentDate

    override fun getDateString(resourceProvider: ResourceProvider): String {
        with(_currentDate.value) {
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
                        Date(_currentDate.value)
                    )
                }
            }
        }
    }

    override fun addDay() {
        _currentDate.update {
            it + DAY_IN_MILLIS
        }
    }

    override fun deductDay() {
        _currentDate.update {
            it - DAY_IN_MILLIS
        }
    }

    private fun Long.isTomorrow() = DateUtils.isToday(this - DateUtils.DAY_IN_MILLIS)

    private fun Long.isYesterday() = DateUtils.isToday(this + DateUtils.DAY_IN_MILLIS)

    private fun Long.isToday() = DateUtils.isToday(this)

    private fun Locale.getCorrectFormat(): SimpleDateFormat {
        val pattern = when(country) {
            "US" -> US_DATE_PATTERN
            else -> EU_DATE_PATTERN
        }

        return SimpleDateFormat(pattern, this)
    }
}