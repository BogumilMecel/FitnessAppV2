package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource

interface UserDataRepository {
    suspend fun saveNutritionValues(nutritionValues: NutritionValues): Resource<Unit>
    suspend fun saveUserInformation(userInformation: UserInformation) : Resource<Unit>
}