package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.product

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import kotlinx.serialization.Serializable

@Serializable
data class NewProductRequest(
    val name: String,
    val measurementUnit: MeasurementUnit,
    val containerWeight: Int? = null,
    val barcode: String? = null,
    val nutritionValuesIn: NutritionValuesIn,
    val nutritionValues: NutritionValues,
    val timestamp: Long
)
