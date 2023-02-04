package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
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
        return try {
            Resource.Success(data = authApi.signIn(request = loginRequest))
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    override suspend fun registerUser(
        username: String,
        email: String,
        password: String,
    ): CustomResult {
        return try {
            val wasAcknowledged = authApi.registerUser(
                RegisterRequest(
                    username = username,
                    email = email,
                    password = password
                )
            )
            return if (wasAcknowledged) CustomResult.Success else handleExceptionWithCustomResult(
                exception = Exception()
            )
        } catch (e: Exception) {
            handleExceptionWithCustomResult(exception = e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): CustomResult {
        return CustomResult.Error(resourceProvider.getString(R.string.unknown_error))
    }
}