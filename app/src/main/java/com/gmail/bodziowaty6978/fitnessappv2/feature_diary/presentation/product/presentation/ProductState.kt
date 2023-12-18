package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData

data class ProductState(
    val product: Product,
    val weight: String = "",
    val nutritionData: NutritionData = NutritionData(),
    val mealName: MealName = MealName.BREAKFAST,
    val priceValue: String = "",
    val priceForValue: String = "",
)
