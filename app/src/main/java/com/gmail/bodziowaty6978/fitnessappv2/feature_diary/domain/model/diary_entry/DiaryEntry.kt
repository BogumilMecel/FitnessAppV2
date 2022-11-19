package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class DiaryEntry(
    val id: String = "",
    val name: String,
    val unit: String,
    val nutritionValues: NutritionValues,
    val timestamp: Long,
    val date: String,
    var weight: Int,
    val mealName: String,
    val product: Product
)