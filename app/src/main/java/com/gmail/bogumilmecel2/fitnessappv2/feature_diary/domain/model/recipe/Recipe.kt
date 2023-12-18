package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.Difficulty
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.TimeRequired
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    val id: String = "",
    val name: String = "",
    val ingredients: List<Ingredient> = emptyList(),
    val utcTimestamp: Long = System.currentTimeMillis(),
    val imageUrl: String? = null,
    val nutritionValues: NutritionValues = NutritionValues(),
    val timeRequired: TimeRequired = TimeRequired.LOW,
    val difficulty: Difficulty = Difficulty.LOW,
    val servings: Int = 0,
    var isPublic: Boolean = false,
    val username: String = "",
    val userId: String = "",
)
