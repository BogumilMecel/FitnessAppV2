package com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider

import kotlinx.coroutines.flow.StateFlow
import java.util.Locale

interface DateProvider {
    val currentDate: StateFlow<Long>

    fun getDateString(
        resourceProvider: ResourceProvider,
        locale: Locale
    ): String

    fun addDay()

    fun deductDay()
}