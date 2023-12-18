package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductDiaryEntryPostRequest(
    val product: Product,
    val timestamp: Long,
    val weight: Int,
    val mealName: MealName,
    val date: String
)
