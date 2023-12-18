package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.Difficulty
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils.TimeRequired
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Recipe(
    @PrimaryKey
    val id: String = "",

    @ColumnInfo(name = "name")
    val name: String = "",

    @ColumnInfo(name = "ingredients")
    val ingredients: List<Ingredient> = emptyList(),

    @ColumnInfo(name = "utc_timestamp")
    val utcTimestamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "nutrition_values")
    val nutritionValues: NutritionValues = NutritionValues(),

    @ColumnInfo(name = "time_required")
    val timeRequired: TimeRequired = TimeRequired.LOW,

    @ColumnInfo(name = "difficulty")
    val difficulty: Difficulty = Difficulty.LOW,

    @ColumnInfo(name = "servings")
    val servings: Int = 0,

    @ColumnInfo(name = "is_public")
    val isPublic: Boolean = false,

    @ColumnInfo(name = "username")
    val username: String = "",

    @ColumnInfo(name = "user_id")
    val userId: String = "",
)