package com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository.UserDataRepository

class SaveNutritionValues(private val userDataRepository: UserDataRepository) {
    suspend operator fun invoke(nutritionValues: NutritionValues): Resource<Unit> {
        return userDataRepository.saveNutritionValues(nutritionValues = nutritionValues)
    }
}