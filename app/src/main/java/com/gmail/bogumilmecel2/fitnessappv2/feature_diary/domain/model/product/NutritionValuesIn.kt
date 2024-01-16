package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.product

import com.gmail.bogumilmecel2.fitnessappv2.R
import com.google.gson.annotations.SerializedName

enum class NutritionValuesIn(val stringResId: Int) {
    @SerializedName("hundred_grams")
    HUNDRED_GRAMS(stringResId = R.string.in_100g),

    @SerializedName("hundred_milliliters")
    HUNDRED_MILLILITERS(stringResId = R.string.in_100ml),

    @SerializedName("container")
    CONTAINER(stringResId = R.string.in_container),

    @SerializedName("average")
    AVERAGE(stringResId = R.string.in_average);
}