package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository

class HandleWeightDialogsQuestionUseCase(
    private val weightRepository: WeightRepository,
    private val cachedValuesProvider: CachedValuesProvider
) {
    suspend operator fun invoke(accepted: Boolean?): Resource<Unit> {
        val resource = weightRepository.handleWeightDialogsQuestion(
            weightDialogsRequest = WeightDialogsRequest(
                accepted = accepted
            )
        )

        cachedValuesProvider.updateLocalLastTimeAskedForWeightDialogs(
            date = CustomDateUtils.getCurrentDateString()
        )

        return when (resource) {
            is Resource.Success -> {
                cachedValuesProvider.updateWeightDialogs(weightDialogs = resource.data)
                Resource.Success(Unit)
            }

            is Resource.Error -> {
                Resource.Error()
            }
        }
    }
}