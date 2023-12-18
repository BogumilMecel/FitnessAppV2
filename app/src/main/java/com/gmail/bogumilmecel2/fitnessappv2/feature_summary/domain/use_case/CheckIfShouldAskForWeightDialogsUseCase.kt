package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository

class CheckIfShouldAskForWeightDialogsUseCase(
    private val weightRepository: WeightRepository
) {
    suspend operator fun invoke(cachedValuesProvider: CachedValuesProvider): Resource<Unit> {
        val localLastTimeAskedForWeightDialogs = cachedValuesProvider.getLocalLastTimeAskedForWeightDialogs()
        val currentDate = CustomDateUtils.getCurrentDateString()

        if (localLastTimeAskedForWeightDialogs == currentDate) return Resource.Error()

        val user = cachedValuesProvider.getUser()

        if (user.weightDialogs?.accepted == true) return Resource.Error()
        if (user.weightDialogs?.lastTimeAsked == currentDate) {
            cachedValuesProvider.updateLocalLastTimeAskedForWeightDialogs(currentDate)
            return Resource.Error()
        }
        if ((user.weightDialogs?.askedCount ?: 1) > 3) return Resource.Error()

        return weightRepository.checkIfShouldAskForWeightDialogs()
    }
}