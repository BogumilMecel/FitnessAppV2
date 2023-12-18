package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlRecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDiaryEntry(
    @SerialName("id")
    override val id: String = "",

    @SerialName("nutrition_values")
    override val nutritionValues: NutritionValues = NutritionValues(),

    @SerialName("utc_timestamp")
    override val utcTimestamp: Long = 0,

    @SerialName("user_id")
    override val userId: String = "",

    @SerialName("date")
    override val date: String = "",

    @SerialName("meal_name")
    override val mealName: MealName = MealName.BREAKFAST,

    @SerialName("edited_utc_timestamp")
    val editedUtcTimestamp: Long = 0,

    @SerialName("recipe_name")
    val recipeName: String = "",

    @SerialName("recipe_id")
    val recipeId: String = "",

    @SerialName("servings")
    val servings: Int = 0,

    @SerialName("deleted")
    val deleted: Boolean = false
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = pluralStringResource(
        id = R.plurals.servings,
        count = servings,
        servings
    )

    override fun getDiaryEntryType() = DiaryEntryType.RECIPE
}

fun SqlRecipeDiaryEntry.toRecipeDiaryEntry() = RecipeDiaryEntry(
    id = id,
    nutritionValues = nutrition_values,
    utcTimestamp = utc_timestamp,
    userId = user_id,
    date = date,
    mealName = meal_name,
    editedUtcTimestamp = edited_utc_timestamp,
    recipeName = recipe_name,
    recipeId = recipe_id,
    servings = servings,
    deleted = deleted
)