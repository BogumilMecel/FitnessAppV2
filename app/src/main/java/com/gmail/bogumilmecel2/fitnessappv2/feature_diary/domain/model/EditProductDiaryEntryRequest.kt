package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import kotlinx.serialization.Serializable

@Serializable
data class EditProductDiaryEntryRequest(
    val productDiaryEntry: ProductDiaryEntry,
    val newWeight: Int
)
