package com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price

class CalculatePricePerServingUseCase {
    operator fun invoke(price: Price, servings: Int) = price.copy(value = price.value * (1.0 / servings.toDouble()))
}