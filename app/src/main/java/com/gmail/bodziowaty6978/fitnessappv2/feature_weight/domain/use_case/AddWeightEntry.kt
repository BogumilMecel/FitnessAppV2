package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.round
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.repository.WeightRepository

class AddWeightEntry(
    private val weightRepository: WeightRepository
) {

    suspend operator fun invoke(value: Double): Resource<NewWeightEntryResponse> {
        val weightEntry = WeightEntry(
            timestamp = System.currentTimeMillis(),
            value = value.round(2)
        )
        return weightRepository.addWeightEntry(weightEntry = weightEntry)
    }
}