package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.round
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.repository.WeightRepository

class AddWeightEntry(
    private val weightRepository: WeightRepository
) {

    suspend operator fun invoke(value: Double): Pair<WeightEntry, Boolean?> {
        val weightEntry = WeightEntry(
            timestamp = System.currentTimeMillis(),
            value = value.round(2)
        )
        return Pair(weightEntry, weightRepository.addWeightEntry(weightEntry = weightEntry).data)
    }
}