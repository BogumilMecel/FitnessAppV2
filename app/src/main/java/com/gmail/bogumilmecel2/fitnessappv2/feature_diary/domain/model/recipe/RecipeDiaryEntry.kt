package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class RecipeDiaryEntry(
    @SerialName("id")
    @PrimaryKey
    override val id: String = "",

    @SerialName("nutrition_values")
    @ColumnInfo("nutrition_values")
    override val nutritionValues: NutritionValues = NutritionValues(),

    @SerialName("utc_timestamp")
    @ColumnInfo("utc_timestamp")
    override val utcTimestamp: Long = 0,

    @SerialName("user_id")
    @ColumnInfo("user_id")
    override val userId: String = "",

    @SerialName("date")
    @ColumnInfo("date")
    override val date: String = "",

    @SerialName("meal_name")
    @ColumnInfo("meal_name")
    override val mealName: MealName = MealName.BREAKFAST,

    @SerialName("edited_utc_timestamp")
    @ColumnInfo("edited_utc_timestamp")
    val editedUtcTimestamp: Long = 0,

    @SerialName("recipe_name")
    @ColumnInfo("recipe_name")
    val recipeName: String = "",

    @SerialName("recipe_id")
    @ColumnInfo("recipe_id")
    val recipeId: String = "",

    @SerialName("servings")
    @ColumnInfo("servings")
    val servings: Int = 0,

    @SerialName("deleted")
    @ColumnInfo("deleted")
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