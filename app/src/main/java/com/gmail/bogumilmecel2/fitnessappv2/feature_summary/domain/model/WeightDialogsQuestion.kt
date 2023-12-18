package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeightDialogsQuestion(
    val askedCount: Int,
    val lastTimeAsked: String
)