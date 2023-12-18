package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.serialization.Serializable

@Serializable
data class  NutritionValues(
    val calories: Int = 0,
    val carbohydrates: Double = 0.0,
    val protein: Double = 0.0,
    val fat: Double = 0.0
)

class NutritionValuesConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromNutritionValues(nutritionValues: NutritionValues): String {
        return gson.toJson(nutritionValues)
    }

    @TypeConverter
    fun toNutritionValues(json: String): NutritionValues {
        return gson.fromJson(json, NutritionValues::class.java)
    }
}

fun NutritionValues.multiplyBy(number: Double): NutritionValues {
    return NutritionValues(
        calories = (calories.toDouble() * number).toInt(),
        carbohydrates = carbohydrates * number,
        protein = protein * number,
        fat = fat * number
    )
}