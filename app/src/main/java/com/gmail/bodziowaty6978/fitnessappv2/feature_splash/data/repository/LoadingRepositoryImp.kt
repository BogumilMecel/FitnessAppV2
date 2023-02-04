package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.AuthenticationRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository

class LoadingRepositoryImp(
    private val loadingApi: LoadingApi,
    private val resourceProvider: ResourceProvider,
    private val customSharedPreferencesUtils: CustomSharedPreferencesUtils
) : LoadingRepository, BaseRepository(resourceProvider) {

    override suspend fun authenticateUser(authenticationRequest: AuthenticationRequest): Resource<User?> {
        return try {
            Resource.Success(loadingApi.authenticate(authenticationRequest)?.let {
                customSharedPreferencesUtils.saveUser(it)
                it
            }
            )
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }
}