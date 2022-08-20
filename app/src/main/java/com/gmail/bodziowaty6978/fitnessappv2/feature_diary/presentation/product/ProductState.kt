package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionData

data class ProductState(
    val weight:String = "",
    val nutritionData:NutritionData = NutritionData(),
    val errorMessage:String? = null,
    val lastErrorMessage:String? = null,
    val mealName:String = ""
)
