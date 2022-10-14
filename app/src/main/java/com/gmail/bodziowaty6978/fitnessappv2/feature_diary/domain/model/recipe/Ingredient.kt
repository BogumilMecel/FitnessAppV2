package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product

data class Ingredient(
    val id: Int = -1,
    val weight: Double = 0.0,
    val product: Product = Product()
)
