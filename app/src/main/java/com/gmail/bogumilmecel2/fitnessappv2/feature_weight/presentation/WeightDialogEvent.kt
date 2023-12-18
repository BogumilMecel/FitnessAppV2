package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.presentation

sealed interface WeightDialogEvent {
    data class EnteredWeight(val value: Double): WeightDialogEvent
    data object ClickedSave: WeightDialogEvent
    data object ClickedCancel: WeightDialogEvent
    data object ClickedBack: WeightDialogEvent
}