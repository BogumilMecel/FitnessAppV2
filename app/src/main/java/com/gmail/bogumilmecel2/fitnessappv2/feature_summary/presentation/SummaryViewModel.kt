package com.gmail.bogumilmecel2.fitnessappv2.feature_summary.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.use_case.SummaryUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val summaryUseCases: SummaryUseCases,
) : BaseViewModel() {

    private val _state = MutableStateFlow(SummaryState())
    val summaryState: StateFlow<SummaryState> = _state

    fun onEvent(event: SummaryEvent) {
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
            summaryUseCases.addWeightEntry(value = value).handle(
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
                    logStreak = cachedValuesProvider.getLatestLogEntry().streak
                )
            }
        }
    }

    private fun getCaloriesSum() {
        viewModelScope.launch {
            val caloriesSum = summaryUseCases.getCaloriesSum(
                date = CurrentDate.dateModel(
                    resourceProvider = resourceProvider
                ).date
            )
            _state.update {
                it.copy(
                    caloriesSum = caloriesSum
                )
            }
        }
    }
}