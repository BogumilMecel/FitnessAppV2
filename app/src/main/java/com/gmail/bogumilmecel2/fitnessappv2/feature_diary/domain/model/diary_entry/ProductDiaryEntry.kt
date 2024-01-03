package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDiaryEntryDto(
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

    @SerialName("product_measurement_unit")
    val productMeasurementUnit: MeasurementUnit? = null,

    @SerialName("product_name")
    val productName: String? = null,

    @SerialName("product_id")
    val productId: String? = null,

    @SerialName("weight")
    val weight: Int? = null,

    @SerialName("deleted")
    val deleted: Boolean = false,

    @SerialName("creation_date")
    val creationDateTime: LocalDateTime? = null,

    @SerialName("change_date")
    val changeDateTime: LocalDateTime? = null
)

@Serializable
data class ProductDiaryEntry(
    override val id: String,
    override val nutritionValues: NutritionValues,
    override val date: LocalDate,
    override val userId: String,
    override val mealName: MealName,
    override val creationDateTime: LocalDateTime,
    override val changeDateTime: LocalDateTime,
    val productMeasurementUnit: MeasurementUnit,
    val productName: String,
    val productId: String,
    val weight: Int,
    val deleted: Boolean,
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = "$weight ${stringResource(id = productMeasurementUnit.getStringRes())}"

    override fun getDiaryEntryType() = DiaryEntryType.PRODUCT
}

fun ProductDiaryEntryDto.toProductDiaryEntry() = try {
    ProductDiaryEntry(
        id = id!!,
        nutritionValues = nutritionValues!!,
        date = date!!,
        userId = userId!!,
        mealName = mealName!!,
        productMeasurementUnit = productMeasurementUnit!!,
        productName = productName!!,
        productId = productId!!,
        weight = weight!!,
        deleted = deleted,
        creationDateTime = creationDateTime!!,
        changeDateTime = changeDateTime!!
    )
} catch (e: Exception) {
    null
}

fun SqlProductDiaryEntry.toProductDiaryEntry() = ProductDiaryEntry(
    id = id,
    nutritionValues = nutrition_values,
    creationDateTime = creation_date_time,
    userId = user_id,
    date = date,
    mealName = meal_name,
    productMeasurementUnit = product_measurement_unit,
    changeDateTime = change_date_time,
    productName = product_name,
    productId = product_id,
    weight = weight,
    deleted = deleted
)