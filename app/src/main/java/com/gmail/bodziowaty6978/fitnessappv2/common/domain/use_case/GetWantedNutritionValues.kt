package com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues

class GetWantedNutritionValues(
    private val customSharedPreferencesUtils: CustomSharedPreferencesUtils
) {
    operator fun invoke():NutritionValues{
        return customSharedPreferencesUtils.getWantedNutritionValues() ?: NutritionValues()
    }
}