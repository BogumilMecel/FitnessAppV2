package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDiaryEntryRequest(
    val recipe: Recipe,
    val servings: Int,
    val timestamp: Long,
    val date: String,
    val mealName: MealName
)
