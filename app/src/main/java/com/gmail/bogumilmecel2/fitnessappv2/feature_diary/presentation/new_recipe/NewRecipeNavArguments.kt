package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName

data class NewRecipeNavArguments(
    val mealName: MealName,
    val date: String
)