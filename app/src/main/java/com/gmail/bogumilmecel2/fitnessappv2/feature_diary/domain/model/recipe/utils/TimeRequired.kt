package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.recipe.utils

import kotlinx.serialization.Serializable

@Serializable
enum class TimeRequired(val displayValue: String) {
    LOW("15 min"),
    AVERAGE("30 min"),
    HIGH("45 min"),
    MORE("60+ min");

    fun getDisplayValueWithoutMin() = displayValue.split(" ").firstOrNull() ?: ""
}
