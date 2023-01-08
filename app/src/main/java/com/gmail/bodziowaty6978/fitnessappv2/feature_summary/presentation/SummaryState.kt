package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation

import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry

data class SummaryState(
    val logStreak: Int? = null,
    val caloriesSum: Int? = null,
    val isWeightPickerVisible: Boolean = false,
    val isAskForWeightPermissionDialogVisible: Boolean = false,
    val weightProgress: String? = null,
    val weightEntries: List<WeightEntry> = emptyList(),
    val latestWeightEntry: WeightEntry? = null,
    val wantedCalories: Int = 0
)
