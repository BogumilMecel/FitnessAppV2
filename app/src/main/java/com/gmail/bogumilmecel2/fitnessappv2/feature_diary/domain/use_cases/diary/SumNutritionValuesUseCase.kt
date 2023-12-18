package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.diary

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues

class SumNutritionValuesUseCase {
    operator fun invoke(nutritionValues: List<NutritionValues>) = NutritionValues(
        calories = nutritionValues.sumOf { it.calories },
        carbohydrates = nutritionValues.sumOf { it.carbohydrates },
        protein = nutritionValues.sumOf { it.protein },
        fat = nutritionValues.sumOf { it.fat },
    )
}