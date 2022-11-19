package com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.repository.UserDataRepository

class SaveNutritionValues(private val userDataRepository: UserDataRepository) {

    suspend operator fun invoke(nutritionValues: NutritionValues): Resource<Boolean> {
        return userDataRepository.saveNutritionValues(nutritionValues = nutritionValues)
    }
}