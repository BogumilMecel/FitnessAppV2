package com.gmail.bodziowaty6978.fitnessappv2.common.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NutritionValues(
    val id:Int = -1,
    val calories:Int = 2850,
    val carbohydrates:Double = 1000.0,
    val protein:Double = 500.0,
    val fat:Double = 100.0
)