package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.use_case.AddWeightEntryUseCase

data class SummaryUseCases(
    val getCaloriesSum: GetCaloriesSum,
    val addWeightEntryUseCase: AddWeightEntryUseCase,
    val checkIfShouldAskForWeightDialogsUseCase: CheckIfShouldAskForWeightDialogsUseCase
)
