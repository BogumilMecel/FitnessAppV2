package com.gmail.bodziowaty6978.fitnessappv2.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NutritionValues(
    val id:Int = 0,
    val calories:Int = 285,
    val carbohydrates:Double = 100.0,
    val protein:Double = 50.0,
    val fat:Double = 10.0
)