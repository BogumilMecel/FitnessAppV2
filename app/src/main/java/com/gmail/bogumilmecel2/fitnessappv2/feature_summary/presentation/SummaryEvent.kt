package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

sealed interface SummaryEvent{
    object DismissedWeightPickerDialog: SummaryEvent
    object ClickedAddWeightEntryButton: SummaryEvent
    data class SavedWeightPickerValue(val value:Double): SummaryEvent
}