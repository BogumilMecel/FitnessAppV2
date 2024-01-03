package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlRecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDiaryEntryDto(
    @SerialName("id")
    val id: String? = null,

    @SerialName("nutrition_values")
    val nutritionValues: NutritionValues? = null,

    @SerialName("date")
    val date: LocalDate? = null,

    @SerialName("user_id")
    val userId: String? = null,

    @SerialName("meal_name")
    val mealName: MealName? = null,

    @SerialName("recipe_name")
    val recipeName: String? = null,

    @SerialName("recipe_id")
    val recipeId: String? = null,

    @SerialName("servings")
    val servings: Int? = null,

    @SerialName("deleted")
    val deleted: Boolean = false,

    @SerialName("creation_date")
    val creationDateTime: LocalDateTime? = null,

    @SerialName("change_date")
    val changeDateTime: LocalDateTime? = null
)

@Serializable
data class RecipeDiaryEntry(
    override val id: String,
    override val nutritionValues: NutritionValues,
    override val date: LocalDate,
    override val userId: String,
    override val mealName: MealName,
    override val creationDateTime: LocalDateTime,
    override val changeDateTime: LocalDateTime,
    val recipeName: String,
    val recipeId: String,
    val servings: Int,
    val deleted: Boolean,
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = pluralStringResource(
        id = R.plurals.servings,
        count = servings,
        servings
    )

    override fun getDiaryEntryType() = DiaryEntryType.RECIPE
}

fun RecipeDiaryEntryDto.toRecipeDiaryEntry() = try {
    RecipeDiaryEntry(
        id = id!!,
        nutritionValues = nutritionValues!!,
        date = date!!,
        userId = userId!!,
        mealName = mealName!!,
        creationDateTime = creationDateTime!!,
        changeDateTime = changeDateTime!!,
        recipeName = recipeName!!,
        recipeId = recipeId!!,
        servings = servings!!,
        deleted = deleted
    )
} catch (e: Exception) {
    null
}

fun SqlRecipeDiaryEntry.toRecipeDiaryEntry() = RecipeDiaryEntryDto(
    id = id,
    nutritionValues = nutrition_values,
    creationDateTime = creation_date_time,
    changeDateTime = change_date_time,
    userId = user_id,
    date = date,
    mealName = meal_name,
    recipeName = recipe_name,
    recipeId = recipe_id,
    servings = servings,
    deleted = deleted
)