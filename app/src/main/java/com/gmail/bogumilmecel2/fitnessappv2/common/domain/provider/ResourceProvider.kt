package com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider

import androidx.annotation.PluralsRes
import androidx.annotation.StringRes

interface ResourceProvider {
    fun getString(@StringRes stringResId: Int, vararg args: Any): String
    fun getPluralString(@PluralsRes pluralResId: Int, quantity: Int): String
    fun mockString(@StringRes stringResId: Int, value: String)
}