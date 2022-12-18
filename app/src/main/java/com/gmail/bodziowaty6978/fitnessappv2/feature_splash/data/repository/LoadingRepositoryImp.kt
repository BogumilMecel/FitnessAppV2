package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.FitnessApp
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogRequest

class LoadingRepositoryImp(
    private val loadingApi:LoadingApi,
    private val resourceProvider: ResourceProvider
):LoadingRepository {

    override suspend fun authenticateUser(): Resource<Boolean> {
        return try {
            loadingApi.authenticate()
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
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
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun addLogEntry(logRequest: LogRequest): Resource<LogEntry> {
        return try {
            Resource.Success(data = loadingApi.addLogEntry(logRequest = logRequest))
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getUnknownErrorString())
        }
    }
}