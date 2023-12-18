package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipePrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData

data class RecipeState(
    val entryData: RecipeEntryData,
    val servings: String = "1",
    val nutritionData: NutritionData = NutritionData(),
    val isUserRecipeOwner: Boolean = true,
    val isIngredientsListExpanded: Boolean = false,
    val isFavorite: Boolean = false,
    val recipePrice: RecipePrice? = null,
    val servingPrice: Double? = null,
    val date: String
)
