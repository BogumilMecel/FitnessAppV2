package com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case.AddWeightEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case.CalculateWeightProgress
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.use_case.GetLatestWeightEntries

data class SummaryUseCases(
    val getLatestLogEntry: GetLatestLogEntry,
    val checkLatestLogEntry: CheckLatestLogEntry,
    val getCaloriesSum: GetCaloriesSum,
    val insertLogEntry: InsertLogEntry,
    val getLatestWeightEntries: GetLatestWeightEntries,
    val addWeightEntry: AddWeightEntry,
    val calculateWeightProgress: CalculateWeightProgress
)
