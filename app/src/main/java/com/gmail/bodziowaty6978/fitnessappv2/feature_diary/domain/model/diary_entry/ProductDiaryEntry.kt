package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import kotlinx.serialization.Serializable

@Serializable
data class ProductDiaryEntry(
    override val id: String = "",
    override val nutritionValues: NutritionValues,
    override val timestamp: Long,
    override val userId: String,
    override val date: String,
    override val mealName: String,
    var weight: Int,
    val product: Product
) : DiaryItem