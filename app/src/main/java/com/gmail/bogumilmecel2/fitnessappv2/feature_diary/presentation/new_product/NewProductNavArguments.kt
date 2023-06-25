package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.new_product

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateTransferObject
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName

data class NewProductNavArguments(
    val mealName: MealName,
    val barcode: String?,
    val dateTransferObject: DateTransferObject
)