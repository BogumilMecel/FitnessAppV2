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
                        setBottomSheetContent(SummaryBottomSheetContent.WeightPicker)
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
                    setBottomSheetContent(SummaryBottomSheetContent.WeightPicker)
                    showBottomBarSheet()
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
            summaryUseCases.handleWeightDialogsQuestionUseCase(
                accepted = accepted,
                cachedValuesProvider = cachedValuesProvider
            ).handle(
                onError = {
                    uiEvent.send(SummaryUiEvent.HideBottomSheet)
                }
            ) {
                delay(1000)
                handlingWeightDialogsQuestion = false
                checkIfShouldShowWeightPicker()
            }
        }
    }

    private fun checkIfShouldShowWeightPicker() {
        viewModelScope.launch {
            summaryUseCases.checkIfShouldShowWeightPickerUseCase(
                cachedValuesProvider = cachedValuesProvider
            ).handle(
                showSnackbar = false
            ) {
                if (_state.value.bottomSheetContent !is  SummaryBottomSheetContent.WeightPicker) {
                    setBottomSheetContent(SummaryBottomSheetContent.WeightPicker)
                }
                showBottomBarSheet()
            }
        }
    }

    private fun setBottomSheetContent(bottomSheetContent: SummaryBottomSheetContent) {
        _state.update {
            it.copy(
                bottomSheetContent = bottomSheetContent
            )
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
                if (_state.value.bottomSheetContent !is SummaryBottomSheetContent.AskForDailyWeightDialogs) {
                    setBottomSheetContent(SummaryBottomSheetContent.AskForDailyWeightDialogs)
                }
                showBottomBarSheet()
            }
        }
    }

    private fun showBottomBarSheet() {
        viewModelScope.launch {
            bottomBarStatusProvider.bottomBarEvent.send(BottomBarEvent.Hide)
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