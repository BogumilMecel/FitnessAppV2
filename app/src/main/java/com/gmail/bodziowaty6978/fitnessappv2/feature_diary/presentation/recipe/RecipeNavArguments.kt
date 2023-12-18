package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe

data class RecipeNavArguments(
    val recipe: Recipe,
    val mealName: String
)
