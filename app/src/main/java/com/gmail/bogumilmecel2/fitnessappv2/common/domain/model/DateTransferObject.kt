package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DateTransferObject(
    val displayedDate: String,
    val realDate: String
)