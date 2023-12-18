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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val summaryUseCases: SummaryUseCases,
    private val bottomBarStatusProvider: BottomBarStatusProvider
) : BaseViewModel<SummaryState, SummaryEvent>(state = SummaryState()) {

    val uiEvent = Channel<SummaryUiEvent>()

    override fun onEvent(event: SummaryEvent) {
        when (event) {
            is SummaryEvent.DismissedWeightPickerDialog -> {
                viewModelScope.launch {
                    bottomBarStatusProvider.bottomBarEvent.send(BottomBarEvent.Show)
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
                    saveNewWeightEntry(value = event.value)
                }
            }

            is SummaryEvent.ClickedAddWeightEntryButton -> {
                viewModelScope.launch {
                    bottomBarStatusProvider.bottomBarEvent.send(BottomBarEvent.Hide)
                    _state.update {
                        it.copy(
                            bottomSheetContent = SummaryBottomSheetContent.WeightPicker
                        )
                    }
                    uiEvent.send(SummaryUiEvent.ShowBottomSheet)
                }
            }

            is SummaryEvent.WeightPickerValueChanged -> {
                _state.update {
                    it.copy(
                        weightPickerCurrentValue = event.value
                    )
                }
            }
        }
    }

    fun initializeData() {
        initBottomBarStatusProvider()
        getLatestLogEntry()
        getCaloriesSum()
        initWeightData()
        initWantedCalories()
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