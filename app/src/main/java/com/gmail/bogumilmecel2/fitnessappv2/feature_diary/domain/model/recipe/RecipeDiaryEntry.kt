package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDiaryEntry(
    override val id: String = "",
    override val nutritionValues: NutritionValues = NutritionValues(),
    override val utcTimestamp: Long = 0,
    override val userId: String = "",
    override val mealName: MealName = MealName.BREAKFAST,
    override val date: String = "",
    val recipeName: String = "",
    val recipeId: String = "",
    val servings: Int = 0,
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = pluralStringResource(
        id = R.plurals.servings,
        count = servings
    )

    override fun getDiaryEntryType() = DiaryEntryType.RECIPE
}

