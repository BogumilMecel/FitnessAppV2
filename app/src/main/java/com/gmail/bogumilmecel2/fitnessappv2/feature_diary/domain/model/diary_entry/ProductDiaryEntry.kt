package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class ProductDiaryEntry(
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

    @ColumnInfo("product_measurement_unit")
    val productMeasurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,

    @ColumnInfo("product_name")
    val productName: String = "",

    @ColumnInfo("product_id")
    val productId: String = "",

    @ColumnInfo("weight")
    val weight: Int = 0,
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = "${this.weight} ${stringResource(id = this.productMeasurementUnit.getStringRes())}"
    override fun getDiaryEntryType() = DiaryEntryType.PRODUCT
}