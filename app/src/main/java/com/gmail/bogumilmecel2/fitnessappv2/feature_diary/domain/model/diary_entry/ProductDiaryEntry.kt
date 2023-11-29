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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class ProductDiaryEntry(
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

    @SerialName("product_measurement_unit")
    @ColumnInfo("product_measurement_unit")
    val productMeasurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,

    @SerialName("edited_utc_timestamp")
    @ColumnInfo("edited_utc_timestamp")
    val editedUtcTimestamp: Long = 0,

    @SerialName("product_name")
    @ColumnInfo("product_name")
    val productName: String = "",

    @SerialName("product_id")
    @ColumnInfo("product_id")
    val productId: String = "",

    @SerialName("weight")
    @ColumnInfo("weight")
    val weight: Int = 0,

    @SerialName("deleted")
    @ColumnInfo("deleted")
    val deleted: Boolean = false
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = "${this.weight} ${stringResource(id = this.productMeasurementUnit.getStringRes())}"
    override fun getDiaryEntryType() = DiaryEntryType.PRODUCT
}