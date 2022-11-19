package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.data.api.WeightApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.repository.WeightRepository

class WeighRepositoryImp(
    private val weightApi: WeightApi,
    private val resourceProvider: ResourceProvider
):WeightRepository {
    override suspend fun getLatestWeightEntries(): Resource<List<WeightEntry>> {
        return try {
            Resource.Success(data = weightApi.getLatestWeightEntries())
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getUnknownErrorString())
        }
    }

    override suspend fun addWeightEntry(weightEntry: WeightEntry): Resource<WeightEntry> {
        return try {
            Resource.Success(data = weightApi.addWeightEntry(weightEntry = weightEntry))
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getUnknownErrorString())
        }
    }
}