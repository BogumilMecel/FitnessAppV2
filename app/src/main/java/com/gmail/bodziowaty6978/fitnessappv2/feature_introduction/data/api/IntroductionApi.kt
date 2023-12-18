package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.api

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import retrofit2.http.Body
import retrofit2.http.POST

interface IntroductionApi {

    @POST("/user/information")
    suspend fun saveUserInformation(
        @Body userInformation: UserInformation
    ):CustomResult

    @POST("/user/nutrition")
    suspend fun saveNutritionValues(
        @Body nutritionValues: NutritionValues
    ):CustomResult
}