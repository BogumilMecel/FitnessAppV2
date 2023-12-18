package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model

@kotlinx.serialization.Serializable
data class WeightEntry(
    val id:String? = null,
    val value: Double,
    val timestamp:Long
)
