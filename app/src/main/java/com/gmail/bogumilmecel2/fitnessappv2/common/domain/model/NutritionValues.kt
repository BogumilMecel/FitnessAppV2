package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class NutritionValues(
    @SerializedName("calories")
    val calories: Int = 0,

    @SerializedName("carbohydrates")
    val carbohydrates: Double = 0.0,

    @SerializedName("protein")
    val protein: Double = 0.0,

    @SerializedName("fat")
    val fat: Double = 0.0
)

fun NutritionValues.multiplyBy(number: Double): NutritionValues {
    return NutritionValues(
        calories = (calories.toDouble() * number).toInt(),
        carbohydrates = carbohydrates * number,
        protein = protein * number,
        fat = fat * number
    )
}