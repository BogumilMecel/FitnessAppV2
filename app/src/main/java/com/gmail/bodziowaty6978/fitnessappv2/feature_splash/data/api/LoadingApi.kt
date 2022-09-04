package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api

import retrofit2.http.GET
import retrofit2.http.Header

interface LoadingApi {

    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization") token:String
    ):Boolean
}