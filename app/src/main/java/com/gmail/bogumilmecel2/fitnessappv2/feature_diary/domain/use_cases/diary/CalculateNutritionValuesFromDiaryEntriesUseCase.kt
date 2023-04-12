package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues

class CalculateNutritionValuesFromDiaryEntriesUseCase {
    operator fun invoke(diaryEntries: List<DiaryItem>) = NutritionValues(
        calories = diaryEntries.sumOf { it.nutritionValues.calories },
        carbohydrates = diaryEntries.sumOf { it.nutritionValues.carbohydrates },
        protein = diaryEntries.sumOf { it.nutritionValues.protein },
        fat = diaryEntries.sumOf { it.nutritionValues.fat },
    )
}