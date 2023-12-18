package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_recipe

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.product.CalculateProductNutritionValues

class CalculateRecipeNutritionValues(
    private val calculateProductNutritionValues: CalculateProductNutritionValues
) {

    operator fun invoke(servings:Int, ingredients:List<Ingredient>): NutritionValues {
        val caloriesSum = ingredients.sumOf { calculateProductNutritionValues(it.weight, it.product).calories } / servings
        val carbohydratesSum = ingredients.sumOf { calculateProductNutritionValues(it.weight, it.product).carbohydrates } / servings
        val proteinSum = ingredients.sumOf { calculateProductNutritionValues(it.weight, it.product).protein } / servings
        val fatSum = ingredients.sumOf { calculateProductNutritionValues(it.weight, it.product).fat } / servings
        return NutritionValues(
            calories = caloriesSum,
            carbohydrates = carbohydratesSum,
            protein = proteinSum,
            fat = fatSum
        )
    }
}