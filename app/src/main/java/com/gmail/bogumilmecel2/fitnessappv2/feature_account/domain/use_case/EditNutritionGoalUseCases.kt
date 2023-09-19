package com.gmail.bogumilmecel2.fitnessappv2.feature_account.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CalculateNutritionValuesPercentages
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.SaveNutritionValues

data class EditNutritionGoalUseCases(
    val calculateNutritionValuesPercentages: CalculateNutritionValuesPercentages,
    val saveNutritionValues: SaveNutritionValues
)
