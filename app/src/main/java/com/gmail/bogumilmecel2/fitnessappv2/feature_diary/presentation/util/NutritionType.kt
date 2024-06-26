package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.util

sealed class NutritionType {
    object Calories: NutritionType()
    object Carbs: NutritionType()
    object Protein: NutritionType()
    object Fat: NutritionType()
}
