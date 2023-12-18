package com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.repository.WeightRepository

class GetLatestWeightEntries(
    private val weightRepository: WeightRepository,
    private val getToken: GetToken,
    private val resourceProvider: ResourceProvider
) {
    suspend operator fun invoke():Resource<List<WeightEntry>>{
        val token = getToken()
        return token?.let {
            weightRepository.getLatestWeightEntries(token = it)
        } ?: Resource.Error(resourceProvider.getUnknownErrorString())
    }
}