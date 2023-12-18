package com.gmail.bogumilmecel2.fitnessappv2.feature_account.presentation

import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.product.domain.model.NutritionData

data class AccountState(
    val nutritionData: NutritionData = NutritionData(),
    val askForWeightDaily: Boolean = false
)
