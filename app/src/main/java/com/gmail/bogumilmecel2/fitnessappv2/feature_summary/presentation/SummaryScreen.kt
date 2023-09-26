package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ConfigureViewModel
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.CaloriesSumSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.LogStreakSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.WeightSection
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.CustomDialog
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.gmail.bogumilmecel2.ui.components.complex.DoubleNumberPicker
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    viewModel.ConfigureViewModel(navigator = navigator)
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val activity = (LocalContext.current as? Activity)

    BackHandler {
        if (state.weightPickerDialogVisible) {
            viewModel.onEvent(SummaryEvent.DismissedWeightPickerDialog)
        } else if (state.isAskForWeightPermissionDialogVisible) {
            viewModel.onEvent(SummaryEvent.DismissedWeightDialogsQuestionDialog)
        } else {
            activity?.finish()
        }
    }

    if (state.weightPickerDialogVisible) {
        CustomDialog(
            title = stringResource(id = R.string.summary_weight_dialog_title),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val startingValue = state.latestWeightEntry?.value ?: 80.0

                    DoubleNumberPicker(
                        modifier = Modifier,
                        value = state.weightPickerCurrentValue,
                        minValue = startingValue - 50.0,
                        maxValue = startingValue + 50.0,
                        onValueChange = {
                            viewModel.onEvent(SummaryEvent.WeightPickerValueChanged(it))
                        }
                    )
                }
            },
            endButtonParams = ButtonParams(
                text = stringResource(id = R.string.save),
                onClick = { viewModel.onEvent(SummaryEvent.SavedWeightPickerValue) }
            ),
            secondaryButtonParams = ButtonParams(
                text = stringResource(id = R.string.cancel),
                onClick = { viewModel.onEvent(SummaryEvent.DismissedWeightPickerDialog) }
            ),
            onDismissRequest = { viewModel.onEvent(SummaryEvent.DismissedWeightPickerDialog) }
        )
    } else if (state.isAskForWeightPermissionDialogVisible) {
        CustomDialog(
            title = stringResource(id = R.string.summary_ask_for_weight_dialogs_title),
            secondaryText = stringResource(id = R.string.summary_ask_for_weight_dialogs_description),
            endButtonParams = ButtonParams(
                text = stringResource(id = R.string.accept),
                onClick = { viewModel.onEvent(SummaryEvent.ClickedAcceptInWeightDialogsQuestion) }
            ),
            secondaryButtonParams = ButtonParams(
                text = stringResource(id = R.string.decline),
                onClick = { viewModel.onEvent(SummaryEvent.ClickedDeclineInWeightDialogsQuestion) }
            ),
            extraButtonParams = ButtonParams(
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