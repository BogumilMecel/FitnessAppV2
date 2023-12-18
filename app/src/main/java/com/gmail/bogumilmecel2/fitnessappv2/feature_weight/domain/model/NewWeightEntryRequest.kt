package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NewWeightEntryRequest(
    val value: Double
)
