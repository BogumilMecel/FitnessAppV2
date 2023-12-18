package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.api

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.domain.model.IntroductionRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserDataApi {

    @POST("/userData/userInformation/")
    suspend fun saveUserInformation(
        @Body introductionRequest: IntroductionRequest
    ): User

    @POST("/userData/nutritionValues/")
    suspend fun saveNutritionValues(
        @Body nutritionValues: NutritionValues
    ): Boolean
}