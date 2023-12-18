package com.gmail.bodziowaty6978.fitnessappv2.feature_account.presentation

sealed interface AccountEvent {
    object OnLogOutButtonClicked:AccountEvent
}