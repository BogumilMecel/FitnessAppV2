package com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

class FormatLocalDateUseCase(private val resourceProvider: ResourceProvider) {
    operator fun invoke(localDate: LocalDate) = when (localDate) {
        CustomDateUtils.getCurrentDate() -> resourceProvider.getString(stringResId = R.string.today)
        CustomDateUtils.getCurrentDate().minus(
            value = 1,
            unit = DateTimeUnit.DAY
        ) -> resourceProvider.getString(stringResId = R.string.yesterday)

        CustomDateUtils.getCurrentDate().plus(
            value = 1,
            unit = DateTimeUnit.DAY
        ) -> resourceProvider.getString(stringResId = R.string.tomorrow)

        else -> localDate.toString()
    }
}