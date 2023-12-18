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
    override suspend fun getLatestWeightEntries(token: String): Resource<List<WeightEntry>> {
        return try {
            Resource.Success(
                data = weightApi.getLatestWeightEntries(token = token)
            )
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getUnknownErrorString())
        }
    }

    override suspend fun addWeightEntry(weightEntry: WeightEntry, token: String): Resource<WeightEntry> {
        return try {
            Resource.Success(
                data = weightApi.addWeightEntry(
                    token = token,
                    weightEntry = weightEntry
                )
            )
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getUnknownErrorString())
        }
    }
}