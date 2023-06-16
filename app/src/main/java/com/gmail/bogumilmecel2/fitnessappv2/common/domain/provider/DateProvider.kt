package com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider

import kotlinx.coroutines.flow.StateFlow

interface DateProvider {
    val currentDate: StateFlow<Long>

    fun getDateString(resourceProvider: ResourceProvider): String

    fun getLocalDateString(): String

    fun addDay()

    fun deductDay()
}