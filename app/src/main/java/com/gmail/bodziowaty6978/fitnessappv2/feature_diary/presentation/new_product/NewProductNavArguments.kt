package com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.new_product

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.domain.model.MealName

data class NewProductNavArguments(
    val mealName: MealName,
    val barcode: String?
)
