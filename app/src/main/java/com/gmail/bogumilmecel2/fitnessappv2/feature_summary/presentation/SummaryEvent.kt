package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

sealed interface SummaryEvent {
    object ClickedNotNowInWeightDialogsQuestion: SummaryEvent
    object ClickedDeclineInWeightDialogsQuestion: SummaryEvent
    object ClickedAcceptInWeightDialogsQuestion: SummaryEvent
    object DismissedWeightPickerDialog : SummaryEvent
    object ClickedBackInWeightPickerDialog : SummaryEvent
    object ClickedAddWeightEntryButton : SummaryEvent
    object SavedWeightPickerValue : SummaryEvent
    data class WeightPickerValueChanged(val value: Double) : SummaryEvent
}
