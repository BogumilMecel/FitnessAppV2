package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductPrice(
    val valueFor100g: Double,
    val valueFor100Calories: Double?,
    val valueFor100Carbohydrates: Double?,
    val valueFor10Protein: Double?,
    val valueFor10Fat: Double?,
)