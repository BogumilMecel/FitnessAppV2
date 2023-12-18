package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model

@kotlinx.serialization.Serializable
data class WeightEntry(
    val id:Int = -1,
    val value: Double,
    val timestamp:Long
)
