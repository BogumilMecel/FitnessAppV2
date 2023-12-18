package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.BackHandler
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.CaloriesSumSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.LogStreakSection
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.WeightSection
import com.gmail.bogumilmecel2.ui.components.base.ButtonParams
import com.gmail.bogumilmecel2.ui.components.base.SheetLayout
import com.gmail.bogumilmecel2.ui.components.complex.DoubleNumberPicker
import com.gmail.bogumilmecel2.ui.components.complex.SimpleSheetLayoutContent
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow

@OptIn(ExperimentalMaterialApi::class)
@Destination
@Composable
fun SummaryScreen(
    viewModel: SummaryViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    val activity = (LocalContext.current as? Activity)
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    LaunchedEffect(key1 = true) {
        viewModel.initializeData()
    }

    BackHandler {
        if (sheetState.currentValue == ModalBottomSheetValue.Expanded) {
            viewModel.onEvent(SummaryEvent.ClickedBackInWeightPickerDialog)
        } else {
            activity?.finish()
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.receiveAsFlow().collectLatest {
            when (it) {
                is SummaryUiEvent.ShowBottomSheet -> {
                    sheetState.show()
                }

                is SummaryUiEvent.HideBottomSheet -> {
                    sheetState.hide()
                }
            }
        }
    }

    SheetLayout(
        sheetState = sheetState,
        bottomSheetContent = {
            when (state.bottomSheetContent) {
                is SummaryBottomSheetContent.WeightPicker -> {
                    SimpleSheetLayoutContent(
                        title = stringResource(id = R.string.summary_weight_dialog_title),
                        content = {
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
                        },
                        firstButtonParams = ButtonParams(
                            text = stringResource(id = R.string.save),
                            onClick = {
                                viewModel.onEvent(SummaryEvent.SavedWeightPickerValue)
                            }
                        ),
                        secondButtonParams = ButtonParams(
                            text = stringResource(id = R.string.cancel),
                            onClick = {
                                viewModel.onEvent(SummaryEvent.ClickedBackInWeightPickerDialog)
                            },
                        )
                    )
                }

                is SummaryBottomSheetContent.AskForDailyWeightDialogs -> {
                    SimpleSheetLayoutContent(
                        title = stringResource(id = R.string.summary_ask_for_weight_dialogs_title),
                        description = stringResource(id = R.string.summary_ask_for_weight_dialogs_description),
                        firstButtonParams = ButtonParams(
                            text = stringResource(id = R.string.accept),
                            onClick = {
                                viewModel.onEvent(SummaryEvent.ClickedAcceptInWeightDialogsQuestion)
                            }
                        ),
                        secondButtonParams = ButtonParams(
                            text = stringResource(id = R.string.decline),
                            onClick = {
                                viewModel.onEvent(SummaryEvent.ClickedDeclineInWeightDialogsQuestion)
                            }
                        ),
                        bottomTextButtonParams = ButtonParams(
                            text = stringResource(id = R.string.ask_me_later),
                            onClick = {
                                viewModel.onEvent(SummaryEvent.ClickedNotNowInWeightDialogsQuestion)
                            }
                        ),
                    )
                }
            }
        },
        onBottomSheetDismissed = {
            viewModel.onEvent(SummaryEvent.DismissedWeightPickerDialog)
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LogStreakSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .padding(top = 12.dp),
                streak = state.logStreak ?: 1
            )

            Spacer(modifier = Modifier.height(12.dp))

            CaloriesSumSection(
                currentCalories = state.caloriesSum ?: 0,
                wantedCalories = state.wantedCalories,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            WeightSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                lastWeightEntry = state.latestWeightEntry?.value,
                weightProgress = state.weightProgress,
                onEvent = {
                    viewModel.onEvent(it)
                }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}