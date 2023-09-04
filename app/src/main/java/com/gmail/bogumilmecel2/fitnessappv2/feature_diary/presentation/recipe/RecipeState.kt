package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.RecipePrice
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData
import com.gmail.bogumilmecel2.ui.components.complex.SearchItemParams

data class RecipeState(
    val servings: String = "1",
    val nutritionData: NutritionData = NutritionData(),
    val isUserRecipeOwner: Boolean = true,
    val isIngredientsListExpanded: Boolean = true,
    val isFavorite: Boolean = false,
    val recipePrice: RecipePrice? = null,
    val servingPrice: Double? = null,
    val date: String = "",
    val ingredientsParams: List<SearchItemParams> = emptyList(),
    val recipeName: String = "",
    val recipeCaloriesText: String = "",
    val timeRequiredText: String = "",
    val difficultyText: String = "",
    val servingsText: String = "",
    val saveButtonText: String = ""
)
