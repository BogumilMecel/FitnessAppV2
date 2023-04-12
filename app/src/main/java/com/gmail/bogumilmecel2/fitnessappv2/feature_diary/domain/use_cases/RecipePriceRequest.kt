package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import kotlinx.serialization.Serializable

@Serializable
data class RecipePriceRequest(
    val ingredients: List<Ingredient>
)