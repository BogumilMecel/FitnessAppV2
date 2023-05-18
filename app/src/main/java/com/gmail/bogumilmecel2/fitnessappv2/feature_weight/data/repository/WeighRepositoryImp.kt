package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.api.WeightApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository

class WeighRepositoryImp(
    private val weightApi: WeightApi,
    realResourceProvider: RealResourceProvider,
    private val customSharedPreferencesUtils: CustomSharedPreferencesUtils
) : WeightRepository, BaseRepository(realResourceProvider) {
    override suspend fun addWeightEntry(weightEntry: WeightEntry): Resource<NewWeightEntryResponse> {
        return handleRequest {
            val response = weightApi.addWeightEntry(weightEntry = weightEntry)
            customSharedPreferencesUtils.updateWeightInfo(newWeightEntryResponse = response)
            response
        }
    }
}