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

@Entity(tableName = "product_diary_entry")
@Serializable
data class ProductDiaryEntry(
    @PrimaryKey
    override val id: String = "",

    @ColumnInfo(name = "nutrition_values")
    override val nutritionValues: NutritionValues = NutritionValues(),

    @ColumnInfo(name = "timestamp_utc")
    override val utcTimestamp: Long = 0,

    @ColumnInfo(name = "user_id")
    override val userId: String = "",

    @ColumnInfo(name = "entry_date")
    override val date: String = "",

    @ColumnInfo(name = "meal_name")
    override val mealName: MealName = MealName.BREAKFAST,

    @ColumnInfo(name = "measurement_unit")
    val productMeasurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,

    @ColumnInfo(name = "product_name")
    val productName: String = "",

    @ColumnInfo(name = "product_id")
    val productId: String = "",

    @ColumnInfo(name = "product_weight")
    val weight: Int = 0,

    @ColumnInfo(name = "last_edited_utc_timestamp")
    val lastEditedUtcTimestamp: Long,
) : DiaryItem {
    @Composable
    override fun getDisplayValue() =
        "${this.weight} ${stringResource(id = this.productMeasurementUnit.getStringRes())}"

    override fun getDiaryEntryType() = DiaryEntryType.PRODUCT
}