package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionResponse

interface UserDataRepository {
    suspend fun saveNutritionValues(nutritionValues: NutritionValues): Resource<Unit>
    suspend fun saveUserInformation(introductionRequest: IntroductionRequest): Resource<IntroductionResponse>
}