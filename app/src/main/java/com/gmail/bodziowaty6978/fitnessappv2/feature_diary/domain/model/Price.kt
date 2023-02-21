package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.Currency
import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val value:Double = 0.0,
    val currency:Currency = Currency.PLN
)