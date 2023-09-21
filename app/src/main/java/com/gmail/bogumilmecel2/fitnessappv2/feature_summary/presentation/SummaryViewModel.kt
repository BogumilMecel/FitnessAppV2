package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.SummaryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val summaryUseCases: SummaryUseCases
) : BaseViewModel<SummaryState, SummaryEvent, Unit>(
    state = SummaryState(),
    navArguments = Unit
) {
    override fun configureOnStart() {
        checkIfShouldAskForWeightDialogs()
        getLatestLogEntry()
        getCaloriesSum()
        initWeightData()
        initWantedCalories()
    }

    override fun onEvent(event: SummaryEvent) {
        when (event) {
            is SummaryEvent.DismissedWeightPickerDialog -> {
                _state.update {
                    it.copy(
                        weightPickerDialogVisible = false
                    )
                }
            }

            is SummaryEvent.SavedWeightPickerValue -> {
                if (!_state.value.isWeightPickerLoading) {
                    _state.update {
                        it.copy(
                            isWeightPickerLoading = true
                        )
                    }
                    saveNewWeightEntry(value = _state.value.weightPickerCurrentValue)
                }
            }

            is SummaryEvent.ClickedAddWeightEntryButton -> {
                _state.update {
                    it.copy(
                        weightPickerDialogVisible = true
                    )
                }
            }

            is SummaryEvent.WeightPickerValueChanged -> {
                _state.update {
                    it.copy(
                        weightPickerCurrentValue = event.value
                    )
                }
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

    private fun handleWeightDialogsAnswer(accepted: Boolean?) {
        viewModelScope.launch {
            summaryUseCases.handleWeightDialogsQuestionUseCase(accepted = accepted).handle(
                onError = {
                    _state.update {
                        it.copy(isAskForWeightPermissionDialogVisible = false)
                    }
                }
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
            ) {
                _state.update {
                    it.copy(weightPickerDialogVisible = true)
                }
            }
        }
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

    private fun saveNewWeightEntry(value: Double) {
        viewModelScope.launch {
            summaryUseCases.addWeightEntryUseCase(value = value).handle(
                finally = {
                    _state.update {
                        it.copy(
                            isWeightPickerLoading = false,
                            weightPickerDialogVisible = false
                        )
                    }
                }
            ) {
                _state.update { state ->
                    state.copy(
                        weightProgress = it.weightProgress,
                        latestWeightEntry = it.latestWeightEntry,
                    )
                }
            }
        }
    }

    private fun initWeightData() {
        viewModelScope.launch(Dispatchers.IO) {
            val latestWeightEntry = cachedValuesProvider.getLatestWeightEntry()
            _state.update {
                it.copy(
                    latestWeightEntry = latestWeightEntry,
                    weightProgress = cachedValuesProvider.getWeightProgress(),
                    weightPickerCurrentValue = latestWeightEntry?.value ?: 80.0
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
                    caloriesSum = summaryUseCases.getCaloriesSum(date = CustomDateUtils.getCurrentDateString())
                )
            }
        }
    }
}