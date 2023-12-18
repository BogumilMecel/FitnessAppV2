package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsLastTimeAsked
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository

class HandleWeightDialogsQuestion(
    private val weightRepository: WeightRepository
) {
    suspend operator fun invoke(
        accepted: Boolean?,
        cachedValuesProvider: CachedValuesProvider
    ): Resource<Boolean> {
        val resource = weightRepository.handleWeightDialogsQuestion(
            weightDialogsRequest = WeightDialogsRequest(
                accepted = accepted
            )
        )

        when (resource) {
            is Resource.Success -> {
                val response = resource.data
                cachedValuesProvider.saveLastTimeAskedForWeightDialogs(weightDialogsLastTImeAsked = response.weightDialogsLastTimeAsked)
                cachedValuesProvider.updateWeightDialogsAccepted(accepted = response.weightDialogsAccepted)
                return Resource.Success(data = response.shouldShowWeightPicker)
            }

            is Resource.Error -> {
                cachedValuesProvider.saveLastTimeAskedForWeightDialogs(
                    weightDialogsLastTImeAsked = WeightDialogsLastTimeAsked(
                        askedCount = cachedValuesProvider.getLastTimeAskedForWeightDialogs()?.askedCount ?: 1,
                        lastTimeAsked = CustomDateUtils.getCurrentDateString()
                    )
                )
                return Resource.Error()
            }
        }
    }
}