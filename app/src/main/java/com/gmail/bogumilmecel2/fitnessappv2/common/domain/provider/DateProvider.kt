package com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider

interface DateProvider {
    fun getDateString(resourceProvider: ResourceProvider): String

    fun getLocalDateString(): String

    fun addDay()

    fun deductDay()
}