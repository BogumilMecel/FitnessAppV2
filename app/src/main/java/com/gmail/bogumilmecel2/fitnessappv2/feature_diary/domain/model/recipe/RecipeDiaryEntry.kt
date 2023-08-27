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
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class RecipeDiaryEntry(
    @PrimaryKey
    override val id: String = "",

    @ColumnInfo("nutrition_values")
    override val nutritionValues: NutritionValues = NutritionValues(),

    @ColumnInfo("utc_timestamp")
    override val utcTimestamp: Long = 0,

    @ColumnInfo("user_id")
    override val userId: String = "",

    @ColumnInfo("date")
    override val date: String = "",

    @ColumnInfo("meal_name")
    override val mealName: MealName = MealName.BREAKFAST,

    @ColumnInfo("recipe_name")
    val recipeName: String = "",

    @ColumnInfo("recipe_id")
    val recipeId: String = "",

    @ColumnInfo("servings")
    val servings: Int = 0,
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = pluralStringResource(
        id = R.plurals.servings,
        count = servings,
        servings
    )

    override fun getDiaryEntryType() = DiaryEntryType.RECIPE
}