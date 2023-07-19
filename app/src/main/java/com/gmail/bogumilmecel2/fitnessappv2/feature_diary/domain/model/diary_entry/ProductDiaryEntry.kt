package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDiaryEntry(
    override val id: String,
    override val nutritionValues: NutritionValues = NutritionValues(),
    override val utcTimestamp: Long = 0,
    override val userId: String = "",
    override val date: String = "",
    override val mealName: MealName = MealName.BREAKFAST,
    val productMeasurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,
    val productName: String = "",
    val productId: String = "",
    val weight: Int = 0,
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = "${this.weight} ${stringResource(id = this.productMeasurementUnit.getStringRes())}"
    override fun getDiaryEntryType() = DiaryEntryType.PRODUCT
}