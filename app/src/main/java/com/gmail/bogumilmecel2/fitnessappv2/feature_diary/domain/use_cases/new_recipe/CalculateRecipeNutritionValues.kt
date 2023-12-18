package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.new_recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient

class CalculateRecipeNutritionValues {

    operator fun invoke(servings:Int, ingredients:List<Ingredient>): NutritionValues {
        val caloriesSum = ingredients.sumOf { it.nutritionValues.calories } / servings
        val carbohydratesSum = ingredients.sumOf { it.nutritionValues.carbohydrates } / servings
        val proteinSum = ingredients.sumOf { it.nutritionValues.protein } / servings
        val fatSum = ingredients.sumOf { it.nutritionValues.fat } / servings
        return NutritionValues(
            calories = caloriesSum,
            carbohydrates = carbohydratesSum,
            protein = proteinSum,
            fat = fatSum
        )
    }
}