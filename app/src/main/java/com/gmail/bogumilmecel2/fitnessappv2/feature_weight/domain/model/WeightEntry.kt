package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeightEntry(
    val value: Double,
    val utcTimestamp: Long,
    val date: String
)
