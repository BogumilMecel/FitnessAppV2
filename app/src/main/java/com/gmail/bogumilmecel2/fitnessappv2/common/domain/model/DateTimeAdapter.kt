package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import app.cash.sqldelight.ColumnAdapter
import kotlinx.datetime.LocalDateTime

class DateTimeAdapter {
    operator fun invoke() = object : ColumnAdapter<LocalDateTime, String> {
        override fun decode(databaseValue: String) = LocalDateTime.parse(databaseValue)
        override fun encode(value: LocalDateTime) = value.toString()
    }
}