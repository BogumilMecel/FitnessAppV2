package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.repository.WeightRepository

class GetLatestWeightEntries(
    private val weightRepository: WeightRepository,
) {
    suspend operator fun invoke():Resource<List<WeightEntry>>{
        return weightRepository.getLatestWeightEntries()
    }
}