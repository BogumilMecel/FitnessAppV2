package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry

data class SummaryState(
    val logStreak: Int? = null,
    val caloriesSum: Int? = null,
    val bottomSheetContent: SummaryBottomSheetContent = SummaryBottomSheetContent.WeightPicker,
    val isAskForWeightPermissionDialogVisible: Boolean = false,
    val weightProgress: String? = null,
    val latestWeightEntry: WeightEntry? = null,
    val wantedCalories: Int = 0,
)

sealed interface SummaryBottomSheetContent {
    object WeightPicker: SummaryBottomSheetContent
    object AskForDailyWeightDialog: SummaryBottomSheetContent
}
