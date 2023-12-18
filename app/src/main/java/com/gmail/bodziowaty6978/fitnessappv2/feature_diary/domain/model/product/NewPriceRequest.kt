package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.product

import kotlinx.serialization.Serializable

@Serializable
data class NewPriceRequest(
    val paidHowMuch: Double,
    val paidForWeight: Int,
)