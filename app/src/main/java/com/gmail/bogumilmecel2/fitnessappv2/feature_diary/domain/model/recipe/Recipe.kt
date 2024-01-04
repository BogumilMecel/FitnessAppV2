package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlRecipe
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.Difficulty
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.TimeRequired
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Recipe(
    @SerialName("id")
    val id: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("ingredients")
    val ingredients: List<Ingredient>? = null,

    @SerialName("image_url")
    val imageUrl: String? = null,

    @SerialName("nutrition_values")
    val nutritionValues: NutritionValues? = null,

    @SerialName("time_required")
    val timeRequired: TimeRequired? = null,

    @SerialName("difficulty")
    val difficulty: Difficulty? = null,

    @SerialName("servings")
    val servings: Int? = null,

    @SerialName("is_public")
    val isPublic: Boolean = false,

    @SerialName("username")
    val username: String? = null,

    @SerialName("user_id")
    val userId: String? = null,

    @SerialName("creation_date")
    val creationDateTime: LocalDateTime? = null
)


fun SqlRecipe.toRecipe() = Recipe(
    id = id,
    name = name,
    ingredients = ingredients,
    creationDateTime = creation_date_time,
    imageUrl = image_url,
    nutritionValues = nutrition_values,
    timeRequired = time_required,
    difficulty = difficulty,
    servings = servings,
    isPublic = is_public,
    username = username,
    userId = user_id
)