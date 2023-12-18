package com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case

import android.content.SharedPreferences
import com.gmail.bogumilmecel2.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency

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