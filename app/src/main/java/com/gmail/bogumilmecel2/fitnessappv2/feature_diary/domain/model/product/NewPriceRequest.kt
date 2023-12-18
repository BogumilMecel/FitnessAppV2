package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product

import kotlinx.serialization.Serializable

@Serializable
data class NewPriceRequest(
    val paidHowMuch: Double,
    val paidForWeight: Int,
)