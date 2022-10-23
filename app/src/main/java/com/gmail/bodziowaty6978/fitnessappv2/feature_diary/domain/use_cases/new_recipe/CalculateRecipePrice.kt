package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases.new_recipe

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.Price
import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Ingredient

class CalculateRecipePrice {

    operator fun invoke(servings: Int, ingredients: List<Ingredient>): Pair<Price, Boolean>{
        var totalPrice = 0.0
        var doAllProductsHavePrice = true
        ingredients.forEach { ingredient ->
            ingredient.product.price?.let { price ->
                totalPrice += price.value
            } ?: kotlin.run {
                doAllProductsHavePrice = false
            }
        }
        return Pair(Price(value = totalPrice / servings), doAllProductsHavePrice)
    }
}