package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository

class SaveAskForWeightDailyUseCase(private val weightRepository: WeightRepository) {
    suspend operator fun invoke(
        accepted: Boolean,
        cachedValuesProvider: CachedValuesProvider
    ): Resource<Unit> {
        val resource = weightRepository.handleWeightDialogsQuestion(
            weightDialogsRequest = WeightDialogsRequest(
                accepted = accepted
            )
        )

        return when (resource) {
            is Resource.Success -> {
                cachedValuesProvider.updateAskForWeightDaily(accepted = accepted)
                Resource.Success(Unit)
            }

            is Resource.Error -> {
                Resource.Error()
            }
        }
    }
}