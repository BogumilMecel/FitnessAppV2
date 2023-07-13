package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeightDialogs(
    val accepted: Boolean? = false,
    val lastTimeAsked: String = "",
    val askedCount: Int = 1
)
