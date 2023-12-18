package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues

data class CalculatedRecipe(
    val nutritionValues: NutritionValues = NutritionValues(),
    val recipe: Recipe = Recipe()
)
