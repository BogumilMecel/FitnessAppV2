package com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValue
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.round

class CalculateNutritionValuesPercentages {

    operator fun invoke(nutritionValues: NutritionValues): Map<NutritionValue, Float>{
        val caloriesFromCarbohydrates = nutritionValues.carbohydrates * 4.0
        val caloriesFromProtein = nutritionValues.protein * 4.0
        val caloriesFromFat = nutritionValues.fat * 9.0

        val sum = caloriesFromCarbohydrates + caloriesFromProtein + caloriesFromFat

        val carbohydratesValue = (((caloriesFromCarbohydrates / sum * 100.0)).round(1)).toFloat()
        val proteinValue = (((caloriesFromProtein / sum * 100.0)).round(1)).toFloat()
        val fatValue = (((caloriesFromFat / sum * 100.0)).round(1)).toFloat()

        return mapOf(
            NutritionValue.CARBOHYDRATES to carbohydratesValue,
            NutritionValue.PROTEIN to proteinValue,
            NutritionValue.FAT to fatValue
        )
    }
}