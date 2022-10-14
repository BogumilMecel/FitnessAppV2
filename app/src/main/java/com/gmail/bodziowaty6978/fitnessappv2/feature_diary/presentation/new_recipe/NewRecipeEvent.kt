package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_recipe

sealed interface NewRecipeEvent{
    object ClickedDifficultyArrow: NewRecipeEvent
    object ClickedTimeArrow: NewRecipeEvent
    object ClickedServingsArrow: NewRecipeEvent
    object ClickedBackArrow: NewRecipeEvent
    data class EnteredServing(val value: String): NewRecipeEvent
    data class SelectedTime(val index: Int): NewRecipeEvent
    data class SelectedDifficulty(val index: Int): NewRecipeEvent
    data class EnteredName(val value:String): NewRecipeEvent
}