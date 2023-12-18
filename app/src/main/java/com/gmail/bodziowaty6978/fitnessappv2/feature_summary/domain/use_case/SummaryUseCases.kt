package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case.AddWeightEntry

data class SummaryUseCases(
    val getCaloriesSum: GetCaloriesSum,
    val addWeightEntry: AddWeightEntry
)
