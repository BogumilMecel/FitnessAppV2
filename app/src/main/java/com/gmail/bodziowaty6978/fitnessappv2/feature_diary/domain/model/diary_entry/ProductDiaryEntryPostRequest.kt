package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product

data class ProductDiaryEntryPostRequest(
    val product: Product,
    val timestamp: Long,
    val weight: Int,
    val mealName: String,
    val date: String
)
