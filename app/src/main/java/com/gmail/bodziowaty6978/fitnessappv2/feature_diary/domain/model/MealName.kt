package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model

import com.gmail.bodziowaty6978.fitnessappv2.R
import kotlinx.serialization.Serializable

@Serializable
enum class MealName {
    BREAKFAST, LUNCH, DINNER, SUPPER;

    fun getDisplayValue() = when (this) {
        BREAKFAST -> R.string.meal_name_breakfast
        LUNCH -> R.string.meal_name_lunch
        DINNER -> R.string.meal_name_dinner
        SUPPER -> R.string.meal_name_supper
    }
}