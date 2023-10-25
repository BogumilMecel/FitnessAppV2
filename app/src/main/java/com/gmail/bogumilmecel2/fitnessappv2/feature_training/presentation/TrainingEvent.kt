package com.gmail.bogumilmecel2.fitnessappv2.feature_training.presentation

sealed interface TrainingEvent {
    data object ClickedCalendarArrowForward: TrainingEvent
    data object ClickedCalendarArrowBackwards: TrainingEvent
}