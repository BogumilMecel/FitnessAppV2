package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeightDialogsLastTimeAsked(
    val lastTimeAsked: String,
    val askedCount: Int = 0
)
