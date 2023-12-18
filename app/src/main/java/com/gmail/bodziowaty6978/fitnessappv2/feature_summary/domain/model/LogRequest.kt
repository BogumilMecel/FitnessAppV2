package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model

@kotlinx.serialization.Serializable
data class LogRequest(
    val timestamp: Long = System.currentTimeMillis()
)
