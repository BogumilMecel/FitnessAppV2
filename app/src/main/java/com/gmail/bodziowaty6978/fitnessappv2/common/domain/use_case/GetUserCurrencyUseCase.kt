package com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case

import android.content.SharedPreferences
import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.Currency

class GetUserCurrencyUseCase(private val sharedPreferences: SharedPreferences) {
    operator fun invoke(): Currency {
        return sharedPreferences.getString(
            CustomSharedPreferencesUtils.CURRENCY,
            null
        )?.let {
            Currency.getCurrencyFromShortName(shortName = it)
        } ?: Currency.PLN
    }
}