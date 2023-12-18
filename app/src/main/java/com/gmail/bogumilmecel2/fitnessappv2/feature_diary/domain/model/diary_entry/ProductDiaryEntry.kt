package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDiaryEntry(
    @SerialName("id")
    override val id: String = "",

    @SerialName("nutrition_values")
    override val nutritionValues: NutritionValues = NutritionValues(),

    @SerialName("utc_timestamp")
    override val utcTimestamp: Long = 0,

    @SerialName("user_id")
    override val userId: String = "",

    @SerialName("date")
    override val date: String = "",

    @SerialName("meal_name")
    override val mealName: MealName = MealName.BREAKFAST,

    @SerialName("product_measurement_unit")
    val productMeasurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,

    @SerialName("edited_utc_timestamp")
    val editedUtcTimestamp: Long = 0,

    @SerialName("product_name")
    val productName: String = "",

    @SerialName("product_id")
    val productId: String = "",

    @SerialName("weight")
    val weight: Int = 0,

    @SerialName("deleted")
    val deleted: Boolean = false
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = "${this.weight} ${stringResource(id = this.productMeasurementUnit.getStringRes())}"
    override fun getDiaryEntryType() = DiaryEntryType.PRODUCT
}

fun SqlProductDiaryEntry.toProductDiaryEntry() = ProductDiaryEntry(
    id = id,
    nutritionValues = nutrition_values,
    utcTimestamp = utc_timestamp,
    userId = user_id,
    date = date,
    mealName = meal_name,
    productMeasurementUnit = product_measurement_unit,
    editedUtcTimestamp = edited_utc_timestamp,
    productName = product_name,
    productId = product_id,
    weight = weight,
    deleted = deleted
)