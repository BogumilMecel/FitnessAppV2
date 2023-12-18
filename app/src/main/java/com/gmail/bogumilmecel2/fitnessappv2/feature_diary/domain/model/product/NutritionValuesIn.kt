package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product

import com.google.gson.annotations.SerializedName

enum class NutritionValuesIn {
    @SerializedName("hundred_grams")
    HUNDRED_GRAMS,

    @SerializedName("container")
    CONTAINER,

    @SerializedName("average")
    AVERAGE;

    companion object {
        fun getValueFromIndex(index: Int) = when (index) {
            1 -> CONTAINER
            2 -> AVERAGE
            else -> HUNDRED_GRAMS
        }
    }
}