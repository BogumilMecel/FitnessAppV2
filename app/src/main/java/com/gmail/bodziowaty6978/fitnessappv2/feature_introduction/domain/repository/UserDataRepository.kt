package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.IntroductionRequest

interface UserDataRepository {
    suspend fun saveNutritionValues(nutritionValues: NutritionValues): Resource<Unit>
    suspend fun saveUserInformation(introductionRequest: IntroductionRequest): Resource<User>
}