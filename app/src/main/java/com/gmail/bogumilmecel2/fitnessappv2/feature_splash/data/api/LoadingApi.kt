package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.api

import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import retrofit2.http.GET
import retrofit2.http.POST

interface LoadingApi {
    @POST("/authentication/authenticate")
    suspend fun authenticate(): User?

    @GET("/")
    suspend fun isReachable()
}