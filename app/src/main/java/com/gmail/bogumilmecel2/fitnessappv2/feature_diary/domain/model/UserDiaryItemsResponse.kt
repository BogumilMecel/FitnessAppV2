package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.Recipe
import kotlinx.serialization.Serializable

@Serializable
data class UserDiaryItemsResponse(
    val userProducts: List<Product>,
    val userRecipes: List<Recipe>
)