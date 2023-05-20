package com.gmail.bogumilmecel2.fitnessappv2.common.data.singleton

import android.content.Context
import android.text.format.DateUtils
import androidx.compose.runtime.mutableStateOf
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateModel
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.resourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.formatToAppDateModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.formatToString
import java.util.Date

object CurrentDate {
    private val date = mutableStateOf(
        System.currentTimeMillis()
    )

    fun dateModel(
        context: Context? = null,
        resourceProvider: ResourceProvider? = null
    ): DateModel {
        return if (resourceProvider != null) {
            date.value.formatToAppDateModel(resourceProvider)
        } else if (context != null) {
            date.value.formatToAppDateModel(
                resourceProvider(context)
            )
        } else {
            DateModel(
                timestamp = System.currentTimeMillis(),
                valueToDisplay = null,
                date = Date(System.currentTimeMillis()).formatToString()
            )
        }
    }

    fun addDay() {
        val newDate = date.value + DateUtils.DAY_IN_MILLIS
        date.value = newDate
    }

    fun deductDay() {
        val newDate = date.value - DateUtils.DAY_IN_MILLIS
        date.value = newDate
    }
}