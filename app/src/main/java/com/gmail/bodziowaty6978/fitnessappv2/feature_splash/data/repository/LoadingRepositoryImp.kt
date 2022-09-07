package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository

class LoadingRepositoryImp(
    private val loadingApi:LoadingApi,
    private val resourceProvider: ResourceProvider
):LoadingRepository {

    override suspend fun authenticateUser(
        token:String
    ): Resource<Boolean> {
        return try {
            val call = loadingApi.authenticate(token = token)
            if (call) {
                Resource.Success(true)
            }
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        } catch (e: Exception) {
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun getNutritionValues(token: String): Resource<NutritionValues> {
        return try {
            return Resource.Success(
                data = loadingApi.getNutritionValues(token)
            )
        }catch (e:Exception){
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }
}