package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case.AddWeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case.CalculateWeightProgress
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case.GetLatestWeightEntries

data class SummaryUseCases(
    val getLatestLogEntry: GetLatestLogEntry,
    val getCaloriesSum: GetCaloriesSum,
    val getLatestWeightEntries: GetLatestWeightEntries,
    val addWeightEntry: AddWeightEntry,
    val calculateWeightProgress: CalculateWeightProgress
)
