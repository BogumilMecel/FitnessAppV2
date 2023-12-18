package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues

data class ProductDiarySearchItem(
    val productId: String,
    val productName: String,
    val nutritionValues: NutritionValues,
    val weight: Int,
    val measurementUnit: MeasurementUnit,
    val timestamp: Long
)