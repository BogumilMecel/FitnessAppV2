package com.gmail.bogumilmecel2.fitnessappv2.common.data.singleton

import android.content.Context
import android.text.format.DateUtils
import androidx.compose.runtime.mutableLongStateOf
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateModel
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.formatToAppDateModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.formatToString
import java.util.Date

object CurrentDate {
    private val date = mutableLongStateOf(
        System.currentTimeMillis()
    )

    fun dateModel(
        context: Context? = null,
        resourceProvider: ResourceProvider? = null
    ): DateModel {
        return if (resourceProvider != null) {
            date.longValue.formatToAppDateModel(resourceProvider)
        } else if (context != null) {
            date.longValue.formatToAppDateModel(
                RealResourceProvider(context)
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
        val newDate = date.longValue + DateUtils.DAY_IN_MILLIS
        date.longValue = newDate
    }

    fun deductDay() {
        val newDate = date.longValue - DateUtils.DAY_IN_MILLIS
        date.longValue = newDate
    }
}