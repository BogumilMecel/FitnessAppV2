package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DeleteDiaryEntryRequest(
    val diaryEntryId: String,
    val diaryEntryType: DiaryEntryType
)
