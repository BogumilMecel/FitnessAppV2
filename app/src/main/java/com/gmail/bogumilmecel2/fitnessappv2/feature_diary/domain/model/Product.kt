package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NutritionValuesIn
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Product(
    @PrimaryKey val id: String = "",
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "container_weight") val containerWeight: Int? = null,
    @ColumnInfo(name = "utc_timestamp") val utcTimestamp: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "nutrition_values_in") val nutritionValuesIn: NutritionValuesIn = NutritionValuesIn.HUNDRED_GRAMS,
    @ColumnInfo(name = "measurement_unit") val measurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,
    @ColumnInfo(name = "nutrition_values") val nutritionValues: NutritionValues = NutritionValues(),
    @ColumnInfo(name = "barcode") val barcode: String? = "",
    @ColumnInfo(name = "username") val username: String = "",
    @ColumnInfo(name = "user_id") val userId: String = ""
)

fun Product.calculateNutritionValues(weight: Int) = NutritionValues(
    calories = ((nutritionValues.calories).toDouble() / 100.0 * weight.toDouble()).toInt(),
    carbohydrates = nutritionValues.carbohydrates / 100.0 * weight.toDouble(),
    protein = nutritionValues.protein / 100.0 * weight.toDouble(),
    fat = nutritionValues.fat / 100.0 * weight.toDouble(),
)