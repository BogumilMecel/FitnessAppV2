package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.let2
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.round
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe

class CalculateRecipeNutritionValuesForServingsUseCase {
    operator fun invoke(
        recipe: Recipe,
        servings: Int
    ): NutritionValues? = with(recipe) {
        return let2(nutritionValues, this.servings) { nutritionValues, recipeServings ->
            val fraction = servings.toDouble() / recipeServings.toDouble()

            with(nutritionValues) {
                NutritionValues(
                    calories = ((calories.toDouble() * fraction).toInt()),
                    carbohydrates = (carbohydrates * fraction).round(2),
                    protein = (protein * fraction).round(2),
                    fat = (fat * fraction).round(2)
                )
            }
        }
    }
}