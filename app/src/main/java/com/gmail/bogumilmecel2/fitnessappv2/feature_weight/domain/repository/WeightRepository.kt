package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightDialogs

interface WeightRepository {
    suspend fun addWeightEntry(
        newWeightEntryRequest: NewWeightEntryRequest,
        timezone: String
    ): Resource<NewWeightEntryResponse>

    suspend fun checkIfShouldAskForWeightDialogs(): Resource<Unit>
    suspend fun handleWeightDialogsQuestion(weightDialogsRequest: WeightDialogsRequest): Resource<WeightDialogs>
}