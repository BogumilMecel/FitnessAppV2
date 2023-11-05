package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.CaloriesSumSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.LogStreakSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.WeightSection
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.CustomDialog
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SummaryScreen(navigator: DestinationsNavigator) {
    hiltViewModel<SummaryViewModel>().ViewModelLayout(navigator = navigator) { viewModel, state ->
        val activity = (LocalContext.current as? Activity)

        BackHandler {
            if (state.isAskForWeightPermissionDialogVisible) {
                viewModel.onEvent(SummaryEvent.DismissedWeightDialogsQuestionDialog)
            } else {
                activity?.finish()
            }
        }

        if (state.isAskForWeightPermissionDialogVisible) {
            CustomDialog(
                title = stringResource(id = R.string.summary_ask_for_weight_dialogs_title),
                secondaryText = stringResource(id = R.string.summary_ask_for_weight_dialogs_description),
                primaryButtonParams = ButtonParams(
                    text = stringResource(id = R.string.accept),
                    onClick = { viewModel.onEvent(SummaryEvent.ClickedAcceptInWeightDialogsQuestion) }
                ),
                secondaryButtonParams = ButtonParams(
                    text = stringResource(id = R.string.decline),
                    onClick = { viewModel.onEvent(SummaryEvent.ClickedDeclineInWeightDialogsQuestion) }
                ),
                extraTextParams = ButtonParams(
                    text = stringResource(id = R.string.ask_me_later),
                    onClick = { viewModel.onEvent(SummaryEvent.ClickedNotNowInWeightDialogsQuestion) }
                ),
                onDismissRequest = { viewModel.onEvent(SummaryEvent.DismissedWeightDialogsQuestionDialog) }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp)
        ) {
            LogStreakSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                streak = state.logStreak ?: 1
            )

            HeightSpacer(12.dp)

            CaloriesSumSection(
                currentCalories = state.caloriesSum ?: 0,
                wantedCalories = state.wantedCalories,
                modifier = Modifier.fillMaxWidth()
            )

            HeightSpacer(12.dp)

            WeightSection(
                modifier = Modifier.fillMaxWidth(),
                lastWeightEntry = state.latestWeightEntry?.value,
                weightProgress = state.weightProgress,
                onEvent = {
                    viewModel.onEvent(it)
                }
            )

            HeightSpacer(12.dp)
        }
    }
}