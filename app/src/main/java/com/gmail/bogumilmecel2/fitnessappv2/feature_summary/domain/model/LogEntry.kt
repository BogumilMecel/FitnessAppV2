package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model

@kotlinx.serialization.Serializable
data class LogEntry(
    val streak:Int = 1,
    val timestamp:Long = System.currentTimeMillis()
)
