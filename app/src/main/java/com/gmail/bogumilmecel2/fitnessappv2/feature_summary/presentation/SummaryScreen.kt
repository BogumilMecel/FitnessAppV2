package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.BaseResult
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.common.util.ViewModelLayout
import com.gmail.bogumilmecel2.fitnessappv2.destinations.WeightDialogScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.CaloriesSumSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.LogStreakSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.WeightSection
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.CustomDialog
import com.gmail.bogumilmecel2.ui.components.base.HeightSpacer
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@Destination
@Composable
fun SummaryScreen(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<WeightDialogScreenDestination, BaseResult>
) {
    hiltViewModel<SummaryViewModel>().ViewModelLayout(navigator = navigator) {
        val state by __state.collectAsStateWithLifecycle()
        val activity = (LocalContext.current as? Activity)

        resultRecipient.onNavResult { result ->
            if (result is NavResult.Value && result.value == BaseResult.Success) {
                (this as? SummaryViewModel)?.initWeightData()
            }
        }

        BackHandler {
            if (state.isAskForWeightPermissionDialogVisible) {
                onEvent(SummaryEvent.DismissedWeightDialogsQuestionDialog)
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
                    onClick = { onEvent(SummaryEvent.ClickedAcceptInWeightDialogsQuestion) }
                ),
                secondaryButtonParams = ButtonParams(
                    text = stringResource(id = R.string.decline),
                    onClick = { onEvent(SummaryEvent.ClickedDeclineInWeightDialogsQuestion) }
                ),
                extraTextParams = ButtonParams(
                    text = stringResource(id = R.string.ask_me_later),
                    onClick = { onEvent(SummaryEvent.ClickedNotNowInWeightDialogsQuestion) }
                ),
                onDismissRequest = { onEvent(SummaryEvent.DismissedWeightDialogsQuestionDialog) }
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
                    onEvent(it)
                }
            )

            HeightSpacer(12.dp)
        }
    }
}