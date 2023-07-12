package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.data.api

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.IntroductionResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface UserDataApi {

    @POST("/userData/userInformation/")
    suspend fun saveUserInformation(
        @Body introductionRequest: IntroductionRequest
    ): IntroductionResponse

    @POST("/userData/nutritionValues/")
    suspend fun saveNutritionValues(
        @Body nutritionValues: NutritionValues
    ): Boolean
}