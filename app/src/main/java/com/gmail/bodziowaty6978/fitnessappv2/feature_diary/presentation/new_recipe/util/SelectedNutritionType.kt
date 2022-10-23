package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.util

sealed interface SelectedNutritionType{
    object Recipe:SelectedNutritionType
    object Serving:SelectedNutritionType
}