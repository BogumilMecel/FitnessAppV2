package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.content.Context
import androidx.annotation.PluralsRes
import androidx.annotation.StringRes
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.ResourceProvider

class RealResourceProvider(private val context: Context): ResourceProvider {

    override fun getString(@StringRes stringResId: Int, vararg args: Any): String {
        return context.getString(stringResId, args)
    }

    override fun getPluralString(@PluralsRes pluralResId: Int, quantity: Int): String {
        return context.resources.getQuantityString(pluralResId, quantity, quantity)
    }

    fun getUnknownErrorString():String{
        return context.getString(R.string.unknown_error)
    }
}