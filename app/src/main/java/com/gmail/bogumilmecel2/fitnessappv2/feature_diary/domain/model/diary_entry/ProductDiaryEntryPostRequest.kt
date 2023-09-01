package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDiaryEntryPostRequest(
    val productId: String,
    val weight: Int,
    val mealName: MealName,
    val date: String,
    val nutritionValues: NutritionValues
)