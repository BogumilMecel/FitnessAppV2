package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlProduct
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NutritionValuesIn
import com.google.gson.annotations.SerializedName
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("container_weight")
    val containerWeight: Int? = null,

    @SerializedName("nutrition_values_in")
    val nutritionValuesIn: NutritionValuesIn = NutritionValuesIn.HUNDRED_GRAMS,

    @SerializedName("measurement_unit")
    val measurementUnit: MeasurementUnit = MeasurementUnit.GRAMS,

    @SerializedName("nutrition_values")
    val nutritionValues: NutritionValues = NutritionValues(),

    @SerializedName("barcode")
    val barcode: String? = "",

    @SerializedName("username")
    val username: String = "",

    @SerializedName("user_id")
    val userId: String = "",

    @SerializedName("date_created")
    val dateCreated: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.UTC)
)

fun SqlProduct.toProduct() = Product(
    id = id,
    name = name,
    containerWeight = container_weight,
    nutritionValuesIn = nutrition_values_in,
    measurementUnit = measurement_unit,
    nutritionValues = nutrition_values,
    barcode = barcode,
    username = username,
    userId = user_id,
    dateCreated = date_created
)