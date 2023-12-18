package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.round

class CalculateServingPrice {
    operator fun invoke(servings: Int, priceValue: Double): Double = (priceValue / servings.toDouble()).round(2)
}