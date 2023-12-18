package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id:String? = null,
    val name: String = "",
    val containerWeight: Int = 0,
    val timestamp:Long = System.currentTimeMillis(),
    val position: Int = 0,
    val unit: String = "g",
    val nutritionValues: NutritionValues = NutritionValues(),
    val barcode: String? = "",
    val price: Price? = null,
    val username: String = "",
    val userId: String = ""
)

fun Product.calculateNutritionValues(weight: Int): NutritionValues{
    return NutritionValues(
        calories = ((nutritionValues.calories).toDouble()/100.0*weight.toDouble()).toInt(),
        carbohydrates = nutritionValues.carbohydrates/100.0*weight.toDouble(),
        protein = nutritionValues.protein/100.0*weight.toDouble(),
        fat = nutritionValues.fat/100.0*weight.toDouble(),
    )
}




