package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource

interface IntroductionRepository {
    suspend fun saveNutritionValues(
        token: String,
        nutritionValues: NutritionValues
    ): Resource<NutritionValues>

    suspend fun saveUserInformation(
        token: String,
        userInformation: UserInformation,
    ) : Resource<UserInformation>

}