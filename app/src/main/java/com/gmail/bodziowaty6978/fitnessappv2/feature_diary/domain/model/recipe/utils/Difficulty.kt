package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.recipe.utils

import kotlinx.serialization.Serializable

@Serializable
enum class Difficulty(val displayValue: String) {
    VERY_LOW(displayValue = "1"),
    LOW(displayValue = "2"),
    AVERAGE(displayValue = "3"),
    HIGH(displayValue = "4"),
    VERY_HIGH(displayValue = "5"),
}