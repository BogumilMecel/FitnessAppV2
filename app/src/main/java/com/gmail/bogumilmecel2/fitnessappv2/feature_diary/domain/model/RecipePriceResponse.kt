package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class RecipePriceResponse(
    val totalPrice: Double,
    val shouldShowPriceWarning: Boolean
)