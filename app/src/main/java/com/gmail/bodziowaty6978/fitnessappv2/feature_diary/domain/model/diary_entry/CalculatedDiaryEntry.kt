package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.diary_entry

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues

data class CalculatedDiaryEntry(
    val nutritionValues: NutritionValues,
    val diaryEntry: DiaryEntry
)
