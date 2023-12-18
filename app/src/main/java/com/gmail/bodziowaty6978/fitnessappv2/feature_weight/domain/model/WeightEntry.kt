package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeightEntry(
    val value: Double,
    val timestamp: Long
)
