package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api.LoadingApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository

class LoadingRepositoryImp(
    private val loadingApi:LoadingApi,
    private val resourceProvider: ResourceProvider,
    private val customSharedPreferencesUtils: CustomSharedPreferencesUtils
):LoadingRepository {

    override suspend fun authenticateUser(
        token:String
    ): Resource<Boolean> {
        return try {
            loadingApi.authenticate(
                token = token
            )
            Resource.Success(true)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun getNutritionValues(token: String): Resource<NutritionValues?> {
        return try {
            var wantedNutritionValues = customSharedPreferencesUtils.getWantedNutritionValues()
            if (wantedNutritionValues == null){
                loadingApi.getNutritionValues(token = token)?.let {
                    customSharedPreferencesUtils.saveWantedNutritionValues(it)
                    wantedNutritionValues = it
                }
            }
            return Resource.Success(
                data = wantedNutritionValues
            )
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }
}