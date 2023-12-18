package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.use_case

data class SummaryUseCases(
    val getLatestLogEntry: GetLatestLogEntry,
    val checkLatestLogEntry: CheckLatestLogEntry,
    val getCaloriesSum: GetCaloriesSum
)
