package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.Difficulty
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.TimeRequired
import kotlinx.serialization.Serializable

@Serializable
data class NewRecipeRequest(
    val recipeName: String,
    val servings: Int,
    val timeRequired: TimeRequired,
    val difficulty: Difficulty,
    val ingredients: List<Ingredient>,
    val isPublic: Boolean
)
