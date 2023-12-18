package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.round

class CalculateSelectedServingPriceUseCase {
    operator fun invoke(
        recipeServings: Int,
        selectedServings: Int,
        totalPrice: Double
    ) = (selectedServings.toDouble() / recipeServings.toDouble() * totalPrice).round(2)
}