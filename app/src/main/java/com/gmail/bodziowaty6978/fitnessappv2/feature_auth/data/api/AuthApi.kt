package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.data.api

import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.AuthRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.TokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/authentication/signup")
    suspend fun registerUser(
        @Body request: AuthRequest
    ): Boolean

    @POST("/authentication/signin")
    suspend fun signIn(
        @Body request: AuthRequest
    ): TokenResponse
}