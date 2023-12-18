package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName

data class DiaryState(
    val diaryEntries: Map<MealName, List<DiaryItem>> = mutableMapOf(),
    val wantedTotalNutritionValues: NutritionValues = NutritionValues(),
    val currentTotalNutritionValues: NutritionValues = NutritionValues(),
    val mealNutritionValues: Map<MealName, NutritionValues> = mutableMapOf(),
    val isDialogShowed: Boolean = false,
    val longClickedDiaryItem: DiaryItem? = null,
    val currentlySelectedMealName: MealName? = null
) {
    fun getDiaryEntries(mealName: MealName) = diaryEntries[mealName] ?: emptyList()
}
