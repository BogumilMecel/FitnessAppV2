package com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.CheckIfWeightIsValidUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.repository.WeightRepository

class AddWeightEntryUseCase(
    private val weightRepository: WeightRepository,
    private val checkIfWeightIsValidUseCase: CheckIfWeightIsValidUseCase
) {

    suspend operator fun invoke(value: Double): Resource<NewWeightEntryResponse> {
        if (!checkIfWeightIsValidUseCase(value)) return Resource.Error()

        return weightRepository.addWeightEntry(
            newWeightEntryRequest = NewWeightEntryRequest(
                value = value,
            ),
            timezone = CustomDateUtils.getCurrentTimezoneId()
        )
    }
}