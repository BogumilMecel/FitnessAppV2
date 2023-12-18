package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product

data class Recipe(
    val id: Int = -1,
    val name: String = "",
    val ingredients: List<Product> = emptyList(),
    val timestamp: Long = System.currentTimeMillis(),
    val imageUrl: String? = null,
    val timeNeeded: Int = 0
)
