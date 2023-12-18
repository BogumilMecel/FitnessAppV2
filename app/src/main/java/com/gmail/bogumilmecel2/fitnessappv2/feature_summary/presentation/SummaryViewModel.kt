package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BottomBarEvent
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BottomBarStatusProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.SummaryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val summaryUseCases: SummaryUseCases,
    private val bottomBarStatusProvider: BottomBarStatusProvider
) : BaseViewModel<SummaryState, SummaryEvent>(state = SummaryState()) {

    val uiEvent = Channel<SummaryUiEvent>()
    private var handlingWeightDialogsQuestion: Boolean = false

    override fun onEvent(event: SummaryEvent) {
        when (event) {
            is SummaryEvent.DismissedWeightPickerDialog -> {
                viewModelScope.launch {
                    bottomBarStatusProvider.bottomBarEvent.send(BottomBarEvent.Show)
                    if (_state.value.bottomSheetContent is SummaryBottomSheetContent.AskForDailyWeightDialogs && !handlingWeightDialogsQuestion) {
                        handleWeightDialogsAnswer(accepted = null)
                        _state.update {
                            it.copy(
                                bottomSheetContent = SummaryBottomSheetContent.WeightPicker
                            )
                        }
                    }
                }
            }

            is SummaryEvent.ClickedBackInWeightPickerDialog -> {
                viewModelScope.launch {
                    uiEvent.send(SummaryUiEvent.HideBottomSheet)
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
                viewModelScope.launch {
                    showBottomBarSheet(bottomSheetContent = SummaryBottomSheetContent.WeightPicker)
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
        }
    }

    fun initializeData() {
        initBottomBarStatusProvider()
        checkIfShouldAskForWeightDialogs()
        getLatestLogEntry()
        getCaloriesSum()
        initWeightData()
        initWantedCalories()
    }

    private fun handleWeightDialogsAnswer(accepted: Boolean?) {
        handlingWeightDialogsQuestion = true
        viewModelScope.launch {
            summaryUseCases.handleWeightDialogsQuestion(
                accepted = accepted,
                cachedValuesProvider = cachedValuesProvider
            ).handle(
                onError = {
                    uiEvent.send(SummaryUiEvent.HideBottomSheet)
                }
            ) { shouldShowWeightPicker ->
                uiEvent.send(SummaryUiEvent.HideBottomSheet)
                if (shouldShowWeightPicker) {
                    delay(1000)
                    _state.update {
                        it.copy(
                            bottomSheetContent = SummaryBottomSheetContent.WeightPicker
                        )
                    }
                    uiEvent.send(SummaryUiEvent.ShowBottomSheet)
                }
                handlingWeightDialogsQuestion = false
            }
        }
    }

    private fun checkIfShouldAskForWeightDialogs() {
        viewModelScope.launch {
            summaryUseCases.checkIfShouldAskForWeightDialogsUseCase(
                cachedValuesProvider = cachedValuesProvider
            ).handle(
                showSnackbar = false
            ) {
                showBottomBarSheet(bottomSheetContent = SummaryBottomSheetContent.AskForDailyWeightDialogs)
            }
        }
    }

    private fun showBottomBarSheet(bottomSheetContent: SummaryBottomSheetContent) {
        viewModelScope.launch {
            bottomBarStatusProvider.bottomBarEvent.send(BottomBarEvent.Hide)
            if (_state.value.bottomSheetContent != bottomSheetContent) {
                _state.update {
                    it.copy(
                        bottomSheetContent = bottomSheetContent
                    )
                }
            }
            uiEvent.send(SummaryUiEvent.ShowBottomSheet)
        }
    }

    private fun initBottomBarStatusProvider() {
        bottomBarStatusProvider.onBottomBarClicked = {
            onEvent(SummaryEvent.ClickedBackInWeightPickerDialog)
        }
    }

    private fun saveNewWeightEntry(value: Double) {
        viewModelScope.launch {
            summaryUseCases.addWeightEntryUseCase(value = value).handle(
                finally = {
                    _state.update {
                        it.copy(
                            isWeightPickerLoading = false
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

                uiEvent.send(SummaryUiEvent.HideBottomSheet)
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
        viewModelScope.launch {
            val caloriesSum = summaryUseCases.getCaloriesSum(
                date = CustomDateUtils.getCurrentDateString()
            )
            _state.update {
                it.copy(
                    caloriesSum = caloriesSum
                )
            }
        }
    }
}