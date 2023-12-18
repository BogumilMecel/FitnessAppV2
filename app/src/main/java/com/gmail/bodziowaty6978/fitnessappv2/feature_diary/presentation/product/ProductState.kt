package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionData

data class ProductState(
    val product: Product,
    val weight: String = "",
    val nutritionData: NutritionData = NutritionData(),
    val mealName: String = "",
    val priceValue: String = "",
    val priceForValue: String = "",
    val recipe:Recipe? = null
)
