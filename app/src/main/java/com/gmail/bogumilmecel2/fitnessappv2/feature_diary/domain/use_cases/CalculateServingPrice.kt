package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.round

class CalculateServingPrice {
    operator fun invoke(servings: Int, priceValue: Double): Double = (priceValue / servings.toDouble()).round(2)
}