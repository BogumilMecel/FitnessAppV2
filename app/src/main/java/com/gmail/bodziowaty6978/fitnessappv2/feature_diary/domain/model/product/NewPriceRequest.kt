package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.product

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.Currency
import kotlinx.serialization.Serializable

@Serializable
data class NewPriceRequest(
    val paidHowMuch: Double,
    val paidForWeight: Int,
    val productId: String,
    val currency: Currency
)