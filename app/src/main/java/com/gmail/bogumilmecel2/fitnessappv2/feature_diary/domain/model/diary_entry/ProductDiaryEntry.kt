package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.diary_entry

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DiaryItem
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.extensions.let2
import com.gmail.bogumilmecel2.fitnessappv2.database.GetProductDiaryEntriesNutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlProductDiaryEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.DiaryEntryType
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDiaryEntry(
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
    override val creationDateTime: LocalDateTime? = null,

    @SerialName("change_date")
    override val changeDateTime: LocalDateTime? = null
) : DiaryItem {
    @Composable
    override fun getDisplayValue() = let2(weight, productMeasurementUnit) { weight, productMeasurementUnit ->
        "$weight ${stringResource(id = productMeasurementUnit.getStringRes())}"
    }

    override fun getDiaryEntryType() = DiaryEntryType.PRODUCT
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

fun List<GetProductDiaryEntriesNutritionValues>.mapNutritionValues() = buildList {
    this@mapNutritionValues.forEach { query ->
        query.nutrition_values?.let { nutritionValues ->
            add(nutritionValues)
        }
    }
}