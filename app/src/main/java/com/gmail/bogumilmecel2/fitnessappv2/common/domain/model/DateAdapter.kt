package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDate

class DateAdapter {
    operator fun invoke() = object : ColumnAdapter<LocalDate, String> {
        override fun decode(databaseValue: String) = LocalDate.parse(databaseValue)
        override fun encode(value: LocalDate) = value.toString()
    }
}