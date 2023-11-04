package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case

data class SummaryUseCases(
    val getCaloriesSum: GetCaloriesSum,
    val checkIfShouldAskForWeightDialogsUseCase: CheckIfShouldAskForWeightDialogsUseCase,
    val handleWeightDialogsQuestionUseCase: HandleWeightDialogsQuestionUseCase,
    val checkIfShouldShowWeightPickerUseCase: CheckIfShouldShowWeightPickerUseCase
)
