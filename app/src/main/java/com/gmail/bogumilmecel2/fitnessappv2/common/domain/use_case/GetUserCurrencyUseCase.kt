package com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency

class GetUserCurrencyUseCase(private val cachedValuesProvider: CachedValuesProvider) {
    suspend operator fun invoke(): Currency = cachedValuesProvider.getUserCurrency()
}