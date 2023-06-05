package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.AuthenticationRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository

class LoadingRepositoryImp(
    private val loadingApi: LoadingApi,
    private val cachedValuesProvider: CachedValuesProvider
) : LoadingRepository, BaseRepository() {

    override suspend fun authenticateUser(authenticationRequest: AuthenticationRequest): Resource<User?> {
        return handleRequest {
            loadingApi.authenticate(authenticationRequest = authenticationRequest)?.let {
                cachedValuesProvider.saveUser(it)
                it
            }
        }
    }
}