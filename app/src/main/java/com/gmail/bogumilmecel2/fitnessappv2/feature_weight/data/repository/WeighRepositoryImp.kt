package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.data.api.WeightApi
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository

class WeighRepositoryImp(private val weightApi: WeightApi) : WeightRepository, BaseRepository() {
    override suspend fun addWeightEntry(weightEntry: WeightEntry): Resource<User> {
        return handleRequest {
            weightApi.addWeightEntry(weightEntry = weightEntry)
        }
    }

    override suspend fun handleWeightDialogsQuestion(weightDialogsRequest: WeightDialogsRequest): Resource<Unit> {
        return handleRequest {
            weightApi.handleWeightDialogAnswer(weightDialogsRequest = weightDialogsRequest)
        }
    }
}