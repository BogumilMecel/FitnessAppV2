package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import retrofit2.http.GET

interface LoadingApi {

    @GET("/authentication/authenticate")
    suspend fun authenticate(): Boolean

    @GET("/userData/nutritionValues/")
    suspend fun getNutritionValues(): NutritionValues?

    @GET("/userData/userInformation")
    suspend fun getUserInformation(): UserInformation?
}