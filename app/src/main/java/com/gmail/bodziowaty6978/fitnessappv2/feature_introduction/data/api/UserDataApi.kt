package com.gmail.bodziowaty6978.fitnessappv2.feature_introduction.data.api

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserDataApi {

    @POST("/userData/userInformation/")
    suspend fun saveUserInformation(
        @Body userInformation: UserInformation
    ): Boolean

    @POST("/userData/nutritionValues/")
    suspend fun saveNutritionValues(
        @Body nutritionValues: NutritionValues
    ): Boolean

    @PUT("/userData/nutritionValues/")
    suspend fun editNutritionValues(
        @Body nutritionValues: NutritionValues
    ): NutritionValues
}