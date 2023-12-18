package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues

class CalculateNutritionValuesFromNutritionValuesUseCase {
    operator fun invoke(nutritionValues: List<NutritionValues>) = NutritionValues(
        calories = nutritionValues.sumOf { it.calories },
        carbohydrates = nutritionValues.sumOf { it.carbohydrates },
        protein = nutritionValues.sumOf { it.protein },
        fat = nutritionValues.sumOf { it.fat },
    )
}