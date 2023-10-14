package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.LoginRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.RegisterRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.TokenResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay

class MockAuthRepository : AuthRepository {
    override suspend fun logInUser(loginRequest: LoginRequest): Resource<TokenResponse> {
        delay(1000)
        return Resource.Success(TokenResponse("token"))
    }

    override suspend fun registerUser(registerRequest: RegisterRequest): Resource<Boolean> {
        delay(1000)
        return Resource.Success(true)
    }

    override suspend fun sendPasswordResetEmail(email: String): Resource<Boolean> {
        delay(1000)
        return Resource.Success(true)
    }
}