package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.recipe

sealed interface RecipeEvent{
    object ClickedBackArrow: RecipeEvent
    object ClickedFavorite: RecipeEvent
    object ClickedExpandIngredientsList: RecipeEvent
    object ClickedSaveRecipeDiaryEntry: RecipeEvent
    data class EnteredServings(val value: String): RecipeEvent
}