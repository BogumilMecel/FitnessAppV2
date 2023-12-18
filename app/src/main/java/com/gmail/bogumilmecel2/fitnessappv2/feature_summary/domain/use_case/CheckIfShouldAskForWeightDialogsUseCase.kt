package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository

class CheckIfShouldAskForWeightDialogsUseCase(
    private val weightRepository: WeightRepository
) {
    suspend operator fun invoke(cachedValuesProvider: CachedValuesProvider): Resource<Unit> {
        val user = cachedValuesProvider.getUser()

        if (user.weightDialogsAccepted == true) return Resource.Error()

        val lastTimeAsked = cachedValuesProvider.getLastTimeAskedForWeightDialogs()

        if (lastTimeAsked != null &&
            (lastTimeAsked.lastTimeAsked == CustomDateUtils.getCurrentDateString() || lastTimeAsked.askedCount > 3)) return Resource.Error()

        return weightRepository.checkIfShouldAskForWeightDialogs()
    }
}