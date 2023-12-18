package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.round
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.repository.WeightRepository

class AddWeightEntry(
    private val weightRepository: WeightRepository,
    private val resourceProvider: ResourceProvider,
    private val getToken: GetToken
) {

    suspend operator fun invoke(value: Double): Resource<WeightEntry> {
        val token = getToken()
        return token?.let {
            val weightEntry = WeightEntry(
                timestamp = System.currentTimeMillis(),
                value = value.round(2),
                id = -1
            )
            weightRepository.addWeightEntry(weightEntry = weightEntry, token = it)
        } ?: Resource.Error(resourceProvider.getUnknownErrorString())
    }
}