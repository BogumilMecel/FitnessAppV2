package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price

class CalculatePrice {
    operator fun invoke(price: Price, weight: Int) = price.copy(value = price.value / 100.0 * weight.toDouble())
}