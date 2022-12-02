package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.data.api.WeightApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.repository.WeightRepository

class WeighRepositoryImp(
    private val weightApi: WeightApi,
    private val customSharedPreferencesUtils: CustomSharedPreferencesUtils,
    private val resourceProvider: ResourceProvider
):WeightRepository {
    override suspend fun getLatestWeightEntries(): Resource<List<WeightEntry>> {
        return try {
            Resource.Success(data = customSharedPreferencesUtils.getLatestWeightEntries())
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getUnknownErrorString())
        }
    }

    override suspend fun addWeightEntry(weightEntry: WeightEntry): Resource<Boolean> {
        return try {
            var wasAcknowledged = false
            if (weightApi.addWeightEntry(weightEntry = weightEntry)){
                customSharedPreferencesUtils.updateLatestWeightEntries(weightEntry = weightEntry)
                wasAcknowledged = true
            }
            Resource.Success(data = wasAcknowledged)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getUnknownErrorString())
        }
    }
}