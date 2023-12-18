package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.diary

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues

sealed class DiaryEvent {
    object ChangedDate:DiaryEvent()
    data class ClickedDiaryEntry(val diaryEntryId:String):DiaryEvent()
    data class ClickedAddProduct(val mealName:String):DiaryEvent()
    data class CollectedWantedNutritionValues(val nutritionValues: NutritionValues):DiaryEvent()
}