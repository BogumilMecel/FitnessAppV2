package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Constants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.destinations.WeightDialogScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.SummaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val summaryUseCases: SummaryUseCases,
) : BaseViewModel<SummaryState, SummaryEvent, Unit>(
    state = SummaryState(),
    navArguments = Unit
) {

    private var latestWeightEntry: WeightEntry? = null

    override fun configureOnStart() {
        getLatestLogEntry()
        getCaloriesSum()
        initWeightData()
        initWantedCalories()
        assignWeightProgress()
    }

    override fun onEvent(event: SummaryEvent) {
        when (event) {
            is SummaryEvent.ClickedAddWeightEntryButton -> {
                navigateToWeightPicker()
            }

            is SummaryEvent.ClickedDeclineInWeightDialogsQuestion -> {
                handleWeightDialogsAnswer(accepted = false)
            }

            is SummaryEvent.ClickedNotNowInWeightDialogsQuestion -> {
                handleWeightDialogsAnswer(accepted = null)
            }

            is SummaryEvent.ClickedAcceptInWeightDialogsQuestion -> {
                handleWeightDialogsAnswer(accepted = true)
            }
            is SummaryEvent.DismissedWeightDialogsQuestionDialog -> {
                _state.update {
                    it.copy(isAskForWeightPermissionDialogVisible = false)
                }
            }
        }
    }

    fun initWeightData() {
        viewModelScope.launch(Dispatchers.IO) {
            latestWeightEntry = cachedValuesProvider.getLatestWeightEntry()
            checkIfShouldAskForWeightDialogs()
            _state.update { it.copy(latestWeightEntry = latestWeightEntry) }
        }
    }

    private fun handleWeightDialogsAnswer(accepted: Boolean?) {
        viewModelScope.launch {
            summaryUseCases.handleWeightDialogsQuestionUseCase(accepted = accepted).handle(
                onError = {
                    _state.update {
                        it.copy(isAskForWeightPermissionDialogVisible = false)
                    }
                },
                showSnackbar = false
            ) {
                _state.update {
                    it.copy(isAskForWeightPermissionDialogVisible = false)
                }
                delay(1000)
                checkIfShouldShowWeightPicker()
            }
        }
    }

    private fun checkIfShouldShowWeightPicker() {
        viewModelScope.launch {
            summaryUseCases.checkIfShouldShowWeightPickerUseCase().handle(
                showSnackbar = false
            ) { navigateToWeightPicker() }
        }
    }

    private fun navigateToWeightPicker() {
        navigateTo(
            destination = WeightDialogScreenDestination(
                startingValue = latestWeightEntry?.value ?: Constants.WEIGHT_PICKER_DEFAULT_VALUE
            )
        )
    }

    private fun checkIfShouldAskForWeightDialogs() {
        viewModelScope.launch {
            summaryUseCases.checkIfShouldAskForWeightDialogsUseCase(
                cachedValuesProvider = cachedValuesProvider
            ).handle(
                showSnackbar = false,
                onError = {
                    checkIfShouldShowWeightPicker()
                }
            ) {
                _state.update {
                    it.copy(isAskForWeightPermissionDialogVisible = true)
                }
            }
        }
    }

    private fun assignWeightProgress() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    weightProgress = cachedValuesProvider.getWeightProgress()?.let { weightProgress ->
                        if (weightProgress > 0) {
                            "+$weightProgress"
                        } else weightProgress.toString()
                    }
                )
            }
        }
    }

    private fun initWantedCalories() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    wantedCalories = cachedValuesProvider.getWantedNutritionValues().calories
                )
            }
        }
    }

    private fun getLatestLogEntry() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    logStreak = cachedValuesProvider.getLogStreak()
                )
            }
        }
    }

    private fun getCaloriesSum() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    caloriesSum = summaryUseCases.getCaloriesSum(date = CustomDateUtils.getCurrentDate())
                )
            }
        }
    }
}