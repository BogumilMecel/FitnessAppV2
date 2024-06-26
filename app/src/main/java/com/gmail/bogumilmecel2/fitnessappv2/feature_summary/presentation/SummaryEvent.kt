package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

sealed interface SummaryEvent {
    data object ClickedNotNowInWeightDialogsQuestion: SummaryEvent
    data object ClickedDeclineInWeightDialogsQuestion: SummaryEvent
    data object ClickedAcceptInWeightDialogsQuestion: SummaryEvent
    data object DismissedWeightDialogsQuestionDialog : SummaryEvent
    data object ClickedAddWeightEntryButton : SummaryEvent
}