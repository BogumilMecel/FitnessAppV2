package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Product
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.utils.Difficulty
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.utils.TimeRequired
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe.util.SelectedNutritionType

sealed interface NewRecipeEvent {
    object ClickedDifficultyArrow : NewRecipeEvent
    object ClickedTimeArrow : NewRecipeEvent
    object ClickedServingsArrow : NewRecipeEvent
    object ClickedBackArrow : NewRecipeEvent
    object ClickedAddNewIngredient : NewRecipeEvent
    data class SwitchedPublic(val value: Boolean) : NewRecipeEvent
    data class EnteredServing(val value: String) : NewRecipeEvent
    data class SelectedTime(val time: TimeRequired) : NewRecipeEvent
    data class SelectedDifficulty(val difficulty: Difficulty) : NewRecipeEvent
    data class EnteredName(val value: String) : NewRecipeEvent
    data class ClickedProduct(val product: Product) : NewRecipeEvent
    data class EnteredSearchText(val value: String) : NewRecipeEvent
    data class EnteredProductWeight(val value: String) : NewRecipeEvent
    data class ChangedSelectedNutritionType(val value: SelectedNutritionType) : NewRecipeEvent
    object ClickedSaveProduct : NewRecipeEvent
    object ClickedIngredientsListArrow : NewRecipeEvent
    object ClickedSearchButton : NewRecipeEvent
    object ClickedSaveRecipe : NewRecipeEvent
}