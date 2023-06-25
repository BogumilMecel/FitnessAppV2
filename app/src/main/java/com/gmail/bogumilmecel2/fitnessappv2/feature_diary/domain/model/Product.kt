package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NutritionValuesIn
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: String = "",
    val name: String = "",
    val containerWeight: Int? = null,
    val utcTimestamp: Long = System.currentTimeMillis(),
    val nutritionValuesIn: NutritionValuesIn = NutritionValuesIn.HUNDRED_GRAMS,
    val measurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,
    val nutritionValues: NutritionValues = NutritionValues(),
    val barcode: String? = "",
    val username: String = "",
    val userId: String = ""
)

fun Product.calculateNutritionValues(weight: Int) = NutritionValues(
    calories = ((nutritionValues.calories).toDouble() / 100.0 * weight.toDouble()).toInt(),
    carbohydrates = nutritionValues.carbohydrates / 100.0 * weight.toDouble(),
    protein = nutritionValues.protein / 100.0 * weight.toDouble(),
    fat = nutritionValues.fat / 100.0 * weight.toDouble(),
)




