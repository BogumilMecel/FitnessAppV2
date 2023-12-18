package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class EditRecipeDiaryEntryRequest(
    val recipeDiaryEntryId: String,
    val newServings: Int
)
