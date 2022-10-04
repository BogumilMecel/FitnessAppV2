package com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.model

@kotlinx.serialization.Serializable
data class LogEntry(
    val id:Int,
    val timestamp:Long,
    val streak:Int
)
