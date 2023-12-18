package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionData

data class RecipeState(
    val recipe: Recipe = Recipe(),
    val portions: String = "1",
    val mealName: MealName = MealName.BREAKFAST,
    val nutritionData: NutritionData = NutritionData(),
    val isUserRecipeOwner: Boolean = true,
    val isIngredientsListExpanded: Boolean = false,
    val isFavorite: Boolean = false,
)
