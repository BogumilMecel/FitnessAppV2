package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry

interface WeightRepository {
    suspend fun addWeightEntry(weightEntry: WeightEntry):Resource<NewWeightEntryResponse>
}