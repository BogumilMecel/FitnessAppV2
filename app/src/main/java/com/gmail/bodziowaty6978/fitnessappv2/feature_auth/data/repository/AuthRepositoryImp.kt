package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.data.api.AuthApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.LoginRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.RegisterRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.TokenResponse
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.repository.AuthRepository

class AuthRepositoryImp(
    private val authApi: AuthApi,
    private val resourceProvider: ResourceProvider
) : AuthRepository, BaseRepository(resourceProvider) {

    override suspend fun logInUser(
        loginRequest: LoginRequest
    ): Resource<TokenResponse> {
        return handleRequest {
            authApi.signIn(request = loginRequest)
        }
    }

    override suspend fun registerUser(
        registerRequest: RegisterRequest
    ): Resource<Boolean> {
        return handleRequest {
            authApi.registerUser(request = registerRequest)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): Resource<Boolean> {
        return Resource.Error(resourceProvider.getString(R.string.unknown_error))
    }
}