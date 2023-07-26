package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class ProductDiaryHistoryItem(
    @PrimaryKey val productId: String,
    @ColumnInfo(name = "product_name") val productName: String,
    @ColumnInfo(name = "nutrition_values") val nutritionValues: NutritionValues,
    @ColumnInfo(name = "weight") val weight: Int,
    @ColumnInfo(name = "measurement_unit") val measurementUnit: MeasurementUnit,
    @ColumnInfo(name = "utc_timestamp") val utcTimestamp: Long
)