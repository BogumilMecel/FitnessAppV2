package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.search

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.DateTransferObject
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.model.MealName

data class SearchNavArguments(
    val mealName: MealName,
    val dateTransferObject: DateTransferObject
)
