package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.FitnessApp
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogRequest

class LoadingRepositoryImp(
    private val loadingApi: LoadingApi,
    private val resourceProvider: ResourceProvider
) : LoadingRepository, BaseRepository(resourceProvider) {

    override suspend fun authenticateUser(): CustomResult {
        return try {
            loadingApi.authenticate()
            CustomResult.Success
        } catch (e: Exception) {
            handleExceptionWithCustomResult(exception = e)
        }
    }

    override suspend fun getUser(): Resource<User?> {
        return try {
            return Resource.Success(
                data = loadingApi.getUser()?.let {
                    FitnessApp.saveUser(it)
                    it
                }
            )
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }

    override suspend fun addLogEntry(logRequest: LogRequest): Resource<LogEntry> {
        return try {
            Resource.Success(data = loadingApi.addLogEntry(logRequest = logRequest))
        } catch (e: Exception) {
            handleExceptionWithResource(exception = e)
        }
    }
}