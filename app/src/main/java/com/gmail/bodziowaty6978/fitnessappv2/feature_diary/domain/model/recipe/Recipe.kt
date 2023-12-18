package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues

@kotlinx.serialization.Serializable
data class Recipe(
    val id: String = "",
    val name: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val timestamp: Long = System.currentTimeMillis(),
    val imageUrl: String? = null,
    val nutritionValues: NutritionValues = NutritionValues(),
    val timeNeeded: Int = 0,
    val difficulty: Int = 0,
    val servings: Int = 0,
    var isPublic: Boolean = false,
    val username: String = "",
    val userId: String = ""
)
