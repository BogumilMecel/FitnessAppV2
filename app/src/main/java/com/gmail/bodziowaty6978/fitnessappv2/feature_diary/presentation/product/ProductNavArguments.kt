package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product

data class ProductNavArguments(
    val mealName: String,
    val product: Product
)