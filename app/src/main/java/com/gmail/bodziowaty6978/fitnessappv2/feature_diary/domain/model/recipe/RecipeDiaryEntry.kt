package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDiaryEntry(
    override val id: String,
    override val nutritionValues: NutritionValues,
    override val timestamp: Long,
    override val userId: String,
    override val mealName: String,
    override val date: String,
    val recipe: Recipe,
    val portions: Int,
): DiaryItem
