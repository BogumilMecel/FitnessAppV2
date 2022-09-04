package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.repository

import android.content.SharedPreferences
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository

class LoadingRepositoryImp(
    private val loadingApi:LoadingApi,
    private val sharedPreferences: SharedPreferences,
    private val resourceProvider: ResourceProvider
):LoadingRepository {

    override suspend fun authenticateUser(
        token:String
    ): Resource<Boolean> {
        return try {
            val call = loadingApi.authenticate(token = token)
            if (call){
                Resource.Success(true)
            }
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }catch (e:Exception){
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun getToken(): Resource<String> {
        return try {
            val token = sharedPreferences.getString("token",null)
            if (token!=null){
             Resource.Success(data = token)
            }
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }catch (e:Exception){
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }

    }
}