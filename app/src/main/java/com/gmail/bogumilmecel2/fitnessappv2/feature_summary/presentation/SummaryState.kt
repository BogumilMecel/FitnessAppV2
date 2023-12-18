package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry

data class SummaryState(
    val logStreak: Int? = null,
    val caloriesSum: Int? = null,
    val isAskForWeightPermissionDialogVisible: Boolean = false,
    val weightProgress: String? = null,
    val latestWeightEntry: WeightEntry? = null,
    val wantedCalories: Int = 0,
    val weightPickerCurrentValue: Double = 80.0,
    val isWeightPickerLoading: Boolean = false,
    val weightPickerDialogVisible: Boolean = false
)