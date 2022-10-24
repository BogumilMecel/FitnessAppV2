package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.api

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface IntroductionApi {

    @POST("/userData/userInformation/")
    suspend fun saveUserInformation(
        @Body userInformation: UserInformation,
        @Header("Authorization") token: String
    ):UserInformation

    @POST("/userData/nutritionValues/")
    suspend fun saveNutritionValues(
        @Body nutritionValues: NutritionValues,
        @Header("Authorization") token: String
    ):NutritionValues
}