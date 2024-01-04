package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.round
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product

class CalculateProductNutritionValuesUseCase {
    operator fun invoke(weight: Int, product: Product): NutritionValues? = with(product.nutritionValues) {
        return this?.let {
            NutritionValues(
                calories = (calories.toDouble() / 100.0 * weight).toInt(),
                carbohydrates = (carbohydrates / 100.0 * weight).round(2),
                protein = (protein / 100.0 * weight).round(2),
                fat = (fat / 100.0 * weight).round(2)
            )
        }
    }
}