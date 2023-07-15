package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDiaryEntryRequest(
    val recipeId: String,
    val servings: Int,
    val date: String,
    val mealName: MealName
)
