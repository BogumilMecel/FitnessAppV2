package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.repository.WeightRepository

class WeighRepositoryImp(

):WeightRepository {
    override suspend fun getLatestWeightEntries(): List<WeightEntry> {
        TODO("Not yet implemented")
    }

    override suspend fun addWeightEntry(weightEntry: WeightEntry): Resource<List<WeightEntry>> {
        TODO("Not yet implemented")
    }
}