package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData

data class ProductState(
    val weight: String = "",
    val nutritionData: NutritionData = NutritionData(),
    val productPrice: ProductPrice? = null,
    val priceValue: String = "",
    val priceForValue: String = "",
    val isSubmitPriceDialogVisible: Boolean = false,
    val productName: String = "",
    val productMeasurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,
    val headerPrimaryText: String = "",
    val headerSecondaryText: String = ""
)
