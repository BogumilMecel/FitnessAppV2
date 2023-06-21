package com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider

interface DateHolder {
    fun getDateString(resourceProvider: ResourceProvider): String

    fun getLocalDateString(): String

    fun addDay()

    fun deductDay()
}