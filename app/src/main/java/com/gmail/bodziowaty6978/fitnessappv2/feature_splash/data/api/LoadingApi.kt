package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import retrofit2.http.GET
import retrofit2.http.Header

interface LoadingApi {

    @GET("/authentication/authenticate")
    suspend fun authenticate(
        @Header("Authorization") token:String
    ):Boolean

    @GET("/nutritionValues")
    suspend fun getNutritionValues(
        @Header("Authorization") token: String
    ):NutritionValues
}