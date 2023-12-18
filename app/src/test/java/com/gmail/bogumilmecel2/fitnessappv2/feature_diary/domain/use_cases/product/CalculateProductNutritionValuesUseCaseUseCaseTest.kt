package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.product

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.Product
import org.junit.Test

internal class CalculateProductNutritionValuesUseCaseUseCaseTest {

    val calculateProductNutritionValuesUseCase = CalculateProductNutritionValuesUseCase()

    @Test
    fun `When provided with random values 1, return give correct nutrition values`() {
        val newValues = calculateProductNutritionValuesUseCase(
            weight = 100,
            product = Product(
                nutritionValues = NutritionValues(
                    calories = 100,
                    protein = 100.0,
                    carbohydrates = 100.0,
                    fat = 100.0
                )
            )
        )
        assert(newValues.calories == 100)
        assert(newValues.carbohydrates == 100.0)
        assert(newValues.protein == 100.0)
        assert(newValues.fat == 100.0)
    }

    @Test
    fun `When provided with random values 2, return give correct nutrition values`() {
        val newValues = calculateProductNutritionValuesUseCase(
            weight = 170,
            product = Product(
                nutritionValues = NutritionValues(
                    calories = 19,
                    protein = 0.9,
                    carbohydrates = 2.9,
                    fat = 0.2
                )
            )
        )
        assert(newValues.calories == 32)
        assert(newValues.carbohydrates == 4.9)
        assert(newValues.protein == 1.5)
        assert(newValues.fat == 0.3)
    }

    @Test
    fun `When provided with random values 3, return give correct nutrition values`() {
        val newValues = calculateProductNutritionValuesUseCase(
            weight = 60,
            product = Product(
                nutritionValues = NutritionValues(
                    calories = 92,
                    protein = 22.0,
                    carbohydrates = 1.0,
                    fat = 0.0
                )
            )
        )
        assert(newValues.calories == 55)
        assert(newValues.carbohydrates == 0.6)
        assert(newValues.protein == 13.2)
        assert(newValues.fat == 0.0)
    }
}