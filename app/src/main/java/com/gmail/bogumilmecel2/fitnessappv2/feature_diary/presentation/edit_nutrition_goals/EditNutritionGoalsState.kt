package com.gmail.bogumilmecel2.fitnessappv2.feature_diary.presentation.edit_nutrition_goals

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues

data class EditNutritionGoalsState(
    val calories:String = "",
    val carbohydratesPercentageValue:Float = 50F,
    val proteinPercentageValue:Float = 50F,
    val fatPercentageValue:Float = 50F,
    val nutritionValues: NutritionValues = NutritionValues(),
    val totalPercentage:Float = 100F
)
