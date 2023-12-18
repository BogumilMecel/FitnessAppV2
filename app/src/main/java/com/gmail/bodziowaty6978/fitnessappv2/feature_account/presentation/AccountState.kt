package com.gmail.bodziowaty6978.fitnessappv2.feature_account.presentation

import com.gmail.bodziowaty6978.fitnessappv2.feature_diary.presentation.product.components.NutritionData

data class AccountState(
    val nutritionData: NutritionData = NutritionData()
)
