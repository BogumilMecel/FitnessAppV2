package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NutritionValuesIn
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Product(
    @SerializedName("id")
    @PrimaryKey
    val id: String = "",

    @SerializedName("name")
    @ColumnInfo(name = "name")
    val name: String = "",

    @SerializedName("container_weight")
    @ColumnInfo(name = "container_weight")
    val containerWeight: Int? = null,

    @SerializedName("utc_timestamp")
    @ColumnInfo(name = "utc_timestamp")
    val utcTimestamp: Long = System.currentTimeMillis(),

    @SerializedName("nutrition_values_in")
    @ColumnInfo(name = "nutrition_values_in")
    val nutritionValuesIn: NutritionValuesIn = NutritionValuesIn.HUNDRED_GRAMS,

    @SerializedName("measurement_unit")
    @ColumnInfo(name = "measurement_unit")
    val measurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,

    @SerializedName("nutrition_values")
    @ColumnInfo(name = "nutrition_values")
    val nutritionValues: NutritionValues = NutritionValues(),

    @SerializedName("barcode")
    @ColumnInfo(name = "barcode")
    val barcode: String? = "",

    @SerializedName("username")
    @ColumnInfo(name = "username")
    val username: String = "",

    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    val userId: String = ""
)