package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CheckIfWeightIsValidUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository

class AddWeightEntryUseCase(
    private val weightRepository: WeightRepository,
    private val checkIfWeightIsValidUseCase: CheckIfWeightIsValidUseCase,
    private val cachedValuesProvider: CachedValuesProvider
) {

    suspend operator fun invoke(value: Double): Resource<Unit> {
        if (!checkIfWeightIsValidUseCase(value)) return Resource.Error()

        val resource = weightRepository.addWeightEntry(
            weightEntry = WeightEntry(
                value = value,
                date = CustomDateUtils.getDate()
            )
        )

        return when(resource) {
            is Resource.Success -> {
                cachedValuesProvider.updateWeightInfo(
                    weightProgress = resource.data.weightProgress,
                    latestWeightEntry = resource.data.latestWeightEntry
                )

                Resource.Success(Unit)
            }

            is Resource.Error -> Resource.Error()
        }
    }
}