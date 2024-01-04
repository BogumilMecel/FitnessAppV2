package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.MeasurementUnit
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.database.SqlProduct
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product.NutritionValuesIn
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("id")
    val id: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("container_weight")
    val containerWeight: Int? = null,

    @SerialName("nutrition_values_in")
    val nutritionValuesIn: NutritionValuesIn? = null,

    @SerialName("measurement_unit")
    val measurementUnit: MeasurementUnit? = null,

    @SerialName("nutrition_values")
    val nutritionValues: NutritionValues? = null,

    @SerialName("barcode")
    val barcode: String? = null,

    @SerialName("username")
    val username: String? = null,

    @SerialName("user_id")
    val userId: String? = null,

    @SerialName("creation_date")
    val creationDateTime: LocalDateTime? = null
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
    creationDateTime = creation_date_time
)