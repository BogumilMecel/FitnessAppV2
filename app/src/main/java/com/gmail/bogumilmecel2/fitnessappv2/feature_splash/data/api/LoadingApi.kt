package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.api

import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.AuthenticationRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import retrofit2.http.Body
import retrofit2.http.POST

interface LoadingApi {
    @POST("/authentication/authenticate")
    suspend fun authenticate(@Body authenticationRequest: AuthenticationRequest): User?
}