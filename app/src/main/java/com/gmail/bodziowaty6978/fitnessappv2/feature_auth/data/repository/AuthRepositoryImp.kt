package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.data.repository

import android.util.Log
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.data.api.AuthApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.AuthRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.TokenResponse
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.repository.AuthRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.TAG

class AuthRepositoryImp(
    private val authApi: AuthApi,
    private val resourceProvider: ResourceProvider
) : AuthRepository {

    override suspend fun logInUser(email: String, password: String): Resource<TokenResponse> {
        return try {
            val token = authApi.signIn(
                AuthRequest(
                    username = email,
                    password = password
                )
            )
            Log.e(TAG,token.token)
            Resource.Success(data = token)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(uiText = resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun registerUser(
        email: String,
        password: String,
    ): CustomResult {
        return try {
            authApi.registerUser(
                AuthRequest(
                    username = email,
                    password = password
                )
            )
            CustomResult.Success
        } catch (e: Exception) {
            e.printStackTrace()
            CustomResult.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): CustomResult {
        return CustomResult.Error(resourceProvider.getString(R.string.unknown_error))
    }
}