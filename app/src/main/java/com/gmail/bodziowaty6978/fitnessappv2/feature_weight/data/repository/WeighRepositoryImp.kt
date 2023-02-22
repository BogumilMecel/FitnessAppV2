package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseRepository
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.data.api.WeightApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.repository.WeightRepository

class WeighRepositoryImp(
    private val weightApi: WeightApi,
    resourceProvider: ResourceProvider,
    private val customSharedPreferencesUtils: CustomSharedPreferencesUtils
) : WeightRepository, BaseRepository(resourceProvider) {
    override suspend fun getLatestWeightEntries(): Resource<List<WeightEntry>> {
        return handleRequest {
            customSharedPreferencesUtils.getWeightEntries()
        }
    }

    override suspend fun addWeightEntry(weightEntry: WeightEntry): Resource<WeightEntry> {
        return handleRequest {
            if (weightApi.addWeightEntry(weightEntry = weightEntry)) {
                customSharedPreferencesUtils.updateLatestWeightEntries(weightEntry = weightEntry)
            }
            weightEntry
        }
    }
}