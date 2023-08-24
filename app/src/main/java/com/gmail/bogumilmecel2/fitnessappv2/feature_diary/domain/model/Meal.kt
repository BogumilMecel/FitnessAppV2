package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues


data class Meal(
    val diaryEntries: List<DiaryItem>,
    val nutritionValues: NutritionValues
) {
    companion object {
        fun createEmpty() = Meal(
            diaryEntries = emptyList(),
            nutritionValues = NutritionValues(
                calories = 0,
                carbohydrates = 0.0,
                protein = 0.0,
                fat = 0.0
            )
        )
    }
}