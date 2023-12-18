package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product

@kotlinx.serialization.Serializable
data class Ingredient(
    val id: Int = -1,
    val weight: Int = 0,
    val product: Product = Product()
)
