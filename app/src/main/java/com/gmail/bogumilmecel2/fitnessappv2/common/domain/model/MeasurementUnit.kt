package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
enum class MeasurementUnit {
    @SerializedName("grams")
    GRAMS,

    @SerializedName("milliliters")
    MILLILITERS;

    fun getStringRes() = when (this) {
        GRAMS -> R.string.measurement_unit_gram
        MILLILITERS -> R.string.measurement_unit_milliliter
    }

    fun getStringResWithValue() = when (this) {
        GRAMS -> R.string.measurement_unit_gram_with_value
        MILLILITERS -> R.string.measurement_unit_milliliter_with_value
    }
}