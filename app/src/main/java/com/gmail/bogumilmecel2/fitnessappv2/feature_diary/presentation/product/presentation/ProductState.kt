package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.presentation

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductPrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData

data class ProductState(
    val product: Product,
    val weight: String = "",
    val nutritionData: NutritionData = NutritionData(),
    val mealName: MealName = MealName.BREAKFAST,
    val productPrice: ProductPrice? = null,
    val priceValue: String = "",
    val priceForValue: String = "",
    val isSubmitPriceDialogVisible: Boolean = false
)
