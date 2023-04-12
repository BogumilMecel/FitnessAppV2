package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeDiaryEntry(
    override val id: String,
    override val nutritionValues: NutritionValues,
    override val timestamp: Long,
    override val userId: String,
    override val mealName: MealName,
    override val date: String,
    val recipe: Recipe,
    val portions: Int,
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = stringResource(
            id = if (this.portions == 1) R.string.diary_serving else R.string.diary_multiple_serving,
            this.portions
        )
    override fun getDiaryEntryType() = DiaryEntryType.RECIPE
}

