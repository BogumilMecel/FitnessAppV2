package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry

interface WeightRepository {
    suspend fun getLatestWeightEntries():List<WeightEntry>
    suspend fun addWeightEntry(weightEntry: WeightEntry):Resource<List<WeightEntry>>
}