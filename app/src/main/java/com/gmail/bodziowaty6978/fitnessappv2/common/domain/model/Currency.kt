package com.gmail.bodziowaty6978.fitnessappv2.common.domain.model

import com.gmail.bodziowaty6978.fitnessappv2.R
import kotlinx.serialization.Serializable

@Serializable
enum class Currency {
    PLN, USD, EUR;

    fun getDisplayValue() = when(this) {
        PLN -> R.string.currency_pln
        USD -> R.string.currency_usd
        EUR -> R.string.currency_eur
    }
}