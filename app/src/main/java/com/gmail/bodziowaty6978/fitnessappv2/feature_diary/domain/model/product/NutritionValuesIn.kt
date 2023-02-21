package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.product

enum class NutritionValuesIn {
    HUNDRED_GRAMS, CONTAINER, AVERAGE;

    companion object {
        fun getValueFromIndex(index: Int) = when(index) {
            1 -> CONTAINER
            2 -> AVERAGE
            else -> HUNDRED_GRAMS
        }
    }
}