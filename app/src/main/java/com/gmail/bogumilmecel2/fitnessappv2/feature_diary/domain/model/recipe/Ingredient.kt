package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import kotlinx.serialization.Serializable

@Serializable
data class Ingredient(
    val weight: Int,
    val productName: String,
    val nutritionValues: NutritionValues,
    val productId: String,
    val measurementUnit: MeasurementUnit,
): java.io.Serializable
