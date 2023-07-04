package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

sealed interface SummaryUiEvent {
    object ShowBottomSheet: SummaryUiEvent
    object HideBottomSheet: SummaryUiEvent
}