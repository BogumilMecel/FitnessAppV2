package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.R
import kotlinx.serialization.Serializable

@Serializable
enum class Currency(val shortName: String) {
    PLN(shortName = "pln"), USD(shortName = "usd"), EUR(shortName = "eur");

    companion object {
        fun getCurrencyFromShortName(shortName: String) = values().find { it.shortName == shortName}
    }

    fun getDisplayValue() = when(this) {
        PLN -> R.string.currency_pln
        USD -> R.string.currency_usd
        EUR -> R.string.currency_eur
    }
}