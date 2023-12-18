package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.use_cases

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.Ingredient
import kotlinx.serialization.Serializable

@Serializable
data class RecipePriceRequest(
    val ingredients: List<Ingredient>
)