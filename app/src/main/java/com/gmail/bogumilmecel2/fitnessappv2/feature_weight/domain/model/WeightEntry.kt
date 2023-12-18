package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeightEntry(
    val value: Double = 0.0,
    val utcTimestamp: Long = 0,
    val date: String = ""
)
