package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeightDialogsResponse(
    val shouldShowWeightPicker: Boolean,
    val weightDialogsAccepted: Boolean,
    val weightDialogsLastTimeAsked: WeightDialogsLastTimeAsked
)
