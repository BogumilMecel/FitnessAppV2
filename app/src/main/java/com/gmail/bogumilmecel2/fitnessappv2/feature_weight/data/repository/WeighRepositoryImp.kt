package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.api.WeightApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository

class WeighRepositoryImp(
    private val weightApi: WeightApi,
    private val cachedValuesProvider: CachedValuesProvider
) : WeightRepository, BaseRepository() {
    override suspend fun addWeightEntry(weightEntry: WeightEntry): Resource<NewWeightEntryResponse> {
        return handleRequest {
            val response = weightApi.addWeightEntry(weightEntry = weightEntry)
            cachedValuesProvider.updateWeightInfo(
                weightProgress = response.weightProgress,
                latestWeightEntry = response.latestWeightEntry
            )
            response
        }
    }
}