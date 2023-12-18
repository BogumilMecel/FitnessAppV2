package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id:Int = -1,
    val name: String = "White rice",
    val containerWeight: Int = 0,
    val position: Int = 0,
    val unit: String = "g",
    val nutritionValues: NutritionValues = NutritionValues(),
    val barcode: String? = "",
    val price: Price? = null
)




