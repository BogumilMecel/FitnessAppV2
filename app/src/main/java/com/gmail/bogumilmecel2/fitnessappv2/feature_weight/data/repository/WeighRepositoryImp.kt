package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.api.WeightApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightDialogs
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository

class WeighRepositoryImp(
    private val weightApi: WeightApi,
    private val cachedValuesProvider: CachedValuesProvider
) : WeightRepository, BaseRepository() {
    override suspend fun addWeightEntry(
        newWeightEntryRequest: NewWeightEntryRequest,
        timezone: String
    ): Resource<NewWeightEntryResponse> {
        return handleRequest {
            val response = weightApi.addWeightEntry(newWeightEntryRequest = newWeightEntryRequest)
            cachedValuesProvider.updateWeightInfo(
                weightProgress = response.weightProgress,
                latestWeightEntry = response.latestWeightEntry
            )
            response
        }
    }

    override suspend fun checkIfShouldAskForWeightDialogs(): Resource<Unit> {
        return handleRequest {
            weightApi.checkIfShouldAskForWeightDialogs()
        }
    }

    override suspend fun handleWeightDialogsQuestion(weightDialogsRequest: WeightDialogsRequest): Resource<WeightDialogs> {
        return handleRequest {
            weightApi.handleWeightDialogAnswer(weightDialogsRequest = weightDialogsRequest)
        }
    }
}