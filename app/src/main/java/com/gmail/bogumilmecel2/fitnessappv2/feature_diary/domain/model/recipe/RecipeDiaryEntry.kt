package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.GetRecipeDiaryEntriesNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlRecipeDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDiaryEntry(
    @SerialName("id")
    override val id: String? = null,

    @SerialName("nutrition_values")
    override val nutritionValues: NutritionValues? = null,

    @SerialName("date")
    override val date: LocalDate? = null,

    @SerialName("user_id")
    override val userId: String? = null,

    @SerialName("meal_name")
    override val mealName: MealName? = null,

    @SerialName("creation_date")
    override val creationDateTime: LocalDateTime? = null,

    @SerialName("change_date")
    override val changeDateTime: LocalDateTime? = null,

    @SerialName("recipe_name")
    val recipeName: String? = null,

    @SerialName("recipe_id")
    val recipeId: String? = null,

    @SerialName("servings")
    val servings: Int? = null,

    @SerialName("deleted")
    val deleted: Boolean = false,
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = servings?.let {
        pluralStringResource(
            id = R.plurals.servings,
            count = servings,
            servings
        )
    }

    override fun getDiaryEntryType() = DiaryEntryType.RECIPE
}

fun SqlRecipeDiaryEntry.toRecipeDiaryEntry() = RecipeDiaryEntry(
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

fun List<GetRecipeDiaryEntriesNutritionValues>.mapNutritionValues() = buildList {
    this@mapNutritionValues.forEach { query ->
        query.nutrition_values?.let { nutritionValues ->
            add(nutritionValues)
        }
    }
}