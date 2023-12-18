package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_recipe

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.ProductResult
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.Difficulty
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.TimeRequired

sealed interface NewRecipeEvent {
    data object ClickedDifficultyArrow : NewRecipeEvent
    data object ClickedTimeArrow : NewRecipeEvent
    data object ClickedServingsArrow : NewRecipeEvent
    data object ClickedBackArrow : NewRecipeEvent
    data object ClickedAddNewIngredient : NewRecipeEvent
    data class LongClickedIngredient(val ingredient: Ingredient) : NewRecipeEvent
    data class SwitchedPublic(val value: Boolean) : NewRecipeEvent
    data class EnteredServing(val value: String) : NewRecipeEvent
    data class SelectedTime(val time: TimeRequired) : NewRecipeEvent
    data class SelectedDifficulty(val difficulty: Difficulty) : NewRecipeEvent
    data class EnteredName(val value: String) : NewRecipeEvent
    data class CheckedNutritionType(val checked: Boolean) : NewRecipeEvent
    data class ReceivedProductResult(val productResult: ProductResult) : NewRecipeEvent
    data object ClickedIngredientsListArrow : NewRecipeEvent
    data object ClickedSaveRecipe : NewRecipeEvent
    data object DismissedDeleteIngredientDialog : NewRecipeEvent
    data object ClickedDeleteInIngredientDialog : NewRecipeEvent
}