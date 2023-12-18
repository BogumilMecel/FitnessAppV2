package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry.ProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipeDiaryEntry
import kotlinx.serialization.Serializable

@Serializable
data class DiaryEntriesResponse(
    val productDiaryEntries: List<ProductDiaryEntry> = emptyList(),
    val recipeDiaryEntries: List<RecipeDiaryEntry> = emptyList()
)
