package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BottomSheetContent
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.SummaryUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation.components.WeightPickerDialog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val summaryUseCases: SummaryUseCases,
) : BaseViewModel<SummaryState, SummaryEvent>(state = SummaryState()) {

    override fun onEvent(event: SummaryEvent) {
        when (event) {
            is SummaryEvent.DismissedWeightPickerDialog -> {
                _state.update {
                    it.copy(
                        isWeightPickerVisible = false
                    )
                }
            }

            is SummaryEvent.SavedWeightPickerValue -> {
                saveNewWeightEntry(value = event.value)
            }

            is SummaryEvent.ClickedAddWeightEntryButton -> {
                _state.update {
                    it.copy(
                        isWeightPickerVisible = true
                    )
                }
                viewModelScope.launch {
                    val startingValue = cachedValuesProvider.getLatestWeightEntry()?.value ?: 80.0
                    showBottomSheet(
                        bottomSheetContent = BottomSheetContent(
                            content = {
                                WeightPickerDialog(
                                    onEvent = {
                                        onEvent(it)
                                    },
                                    startingValue = startingValue
                                )
                            },
                            onBottomSheetClosed = {
                                onEvent(SummaryEvent.DismissedWeightPickerDialog)
                            }
                        ),
                    )
                }
            }
        }
    }

    fun initializeData() {
        getLatestLogEntry()
        getCaloriesSum()
        initWeightData()
        initWantedCalories()
    }

    private fun saveNewWeightEntry(value: Double) {
        viewModelScope.launch {
            summaryUseCases.addWeightEntryUseCase(value = value).handle(
                finally = {
                    _state.update {
                        it.copy(
                            isWeightPickerVisible = false
                        )
                    }
                }
            ) {
                _state.update { state ->
                    state.copy(
                        weightProgress = it.weightProgress,
                        latestWeightEntry = it.latestWeightEntry
                    )
                }
            }
        }
    }

    private fun initWeightData() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    latestWeightEntry = cachedValuesProvider.getLatestWeightEntry(),
                    weightProgress = cachedValuesProvider.getWeightProgress()
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