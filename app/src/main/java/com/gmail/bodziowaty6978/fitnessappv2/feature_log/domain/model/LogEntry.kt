package com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.model

@kotlinx.serialization.Serializable
data class LogEntry(
    val streak:Int = 0,
    val timestamp:Long = System.currentTimeMillis()
)
