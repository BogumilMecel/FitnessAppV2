package com.gmail.bogumilmecel2.fitnessappv2.feature_training.presentation.training

sealed interface TrainingEvent {
    data object ClickedCalendarArrowForward: TrainingEvent
    data object ClickedCalendarArrowBackwards: TrainingEvent
    data object ClickedAddButton: TrainingEvent
    data object ClickedCopyButton: TrainingEvent
}