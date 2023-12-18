package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlRecipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.Difficulty
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.TimeRequired
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    @SerialName("id")
    val id: String = "",

    @SerialName("name")
    val name: String = "",

    @SerialName("ingredients")
    val ingredients: List<Ingredient> = emptyList(),

    @SerialName("utc_timestamp")
    val utcTimestamp: Long = System.currentTimeMillis(),

    @SerialName("image_url")
    val imageUrl: String? = null,

    @SerialName("nutrition_values")
    val nutritionValues: NutritionValues = NutritionValues(),

    @SerialName("time_required")
    val timeRequired: TimeRequired = TimeRequired.LOW,

    @SerialName("difficulty")
    val difficulty: Difficulty = Difficulty.LOW,

    @SerialName("servings")
    val servings: Int = 0,

    @SerialName("is_public")
    val isPublic: Boolean = false,

    @SerialName("username")
    val username: String = "",

    @SerialName("user_id")
    val userId: String = "",
)

fun SqlRecipe.toRecipe() = Recipe(
    id = id,
    name = name,
    ingredients = ingredients,
    utcTimestamp = utc_timestamp,
    imageUrl = image_url,
    nutritionValues = nutrition_values,
    timeRequired = time_required,
    difficulty = difficulty,
    servings = servings,
    isPublic = is_public,
    username = username,
    userId = user_id
)