package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipePrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData

data class RecipeState(
    val recipe: Recipe = Recipe(),
    val servings: String = "1",
    val mealName: MealName = MealName.BREAKFAST,
    val nutritionData: NutritionData = NutritionData(),
    val isUserRecipeOwner: Boolean = true,
    val isIngredientsListExpanded: Boolean = false,
    val isFavorite: Boolean = false,
    val recipePrice: RecipePrice? = null,
    val servingPrice: Double? = null
)
