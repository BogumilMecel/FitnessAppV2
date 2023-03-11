package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.presentation

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product

data class ProductNavArguments(
    val mealName: MealName,
    val product: Product
)
