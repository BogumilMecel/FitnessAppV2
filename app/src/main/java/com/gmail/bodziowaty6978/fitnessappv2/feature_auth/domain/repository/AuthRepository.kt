package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.LoginRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.TokenResponse

interface AuthRepository {

    suspend fun logInUser(
        loginRequest: LoginRequest
    ): Resource<TokenResponse>

    suspend fun registerUser(
        username: String,
        email: String,
        password: String
    ): CustomResult

    suspend fun sendPasswordResetEmail(
        email: String
    ): CustomResult
}