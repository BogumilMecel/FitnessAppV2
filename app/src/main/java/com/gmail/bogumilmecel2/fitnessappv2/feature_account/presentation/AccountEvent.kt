package com.gmail.bogumilmecel2.fitnessappv2.feature_account.presentation

sealed interface AccountEvent {
    data object ClickedLogOutButtonClicked : AccountEvent
    data object ClickedEditNutritionGoals : AccountEvent
    data object BackPressed : AccountEvent
    data class AskForWeightDailyClicked(val checked: Boolean) : AccountEvent
}