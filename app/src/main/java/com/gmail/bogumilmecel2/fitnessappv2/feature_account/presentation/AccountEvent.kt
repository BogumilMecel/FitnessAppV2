package com.gmail.bogumilmecel2.fitnessappv2.feature_account.presentation

sealed interface AccountEvent {
    object ClickedLogOutButtonClicked:AccountEvent
    object ClickedEditNutritionGoals:AccountEvent
    object BackPressed:AccountEvent
}