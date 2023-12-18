package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Meal
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName

data class DiaryState(
    val diaryEntries: Map<MealName, Meal> = emptyMap(),
    val wantedTotalNutritionValues: NutritionValues = NutritionValues(),
    val currentTotalNutritionValues: NutritionValues = NutritionValues(),
    val longClickedDiaryItemParams: LongClickedDiaryItemParams? = null,
    val currentlySelectedMealName: MealName? = null,
    val displayedDate: String = "",
)

data class LongClickedDiaryItemParams(
    val longClickedDiaryItem: DiaryItem,
    val dialogTitle: String
)