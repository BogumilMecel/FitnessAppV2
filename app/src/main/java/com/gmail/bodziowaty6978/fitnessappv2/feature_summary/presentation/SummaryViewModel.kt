package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.use_case.SummaryUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
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
        getLatestWeightEntries()
        initWantedCalories()
    }

    private fun saveNewWeightEntry(value: Double) {
        viewModelScope.launch {
            val resource = summaryUseCases.addWeightEntry(value = value)
            if (resource.second == true) {
                _state.update { state ->
                    state.weightEntries.toMutableList().apply {
                        add(resource.first)
                        setWeightEntries(weightEntries = this)
                    }
                    state.copy(
                        isWeightPickerVisible = false,
                    )
                }
            }
            _state.update {
                it.copy(
                    isWeightPickerVisible = false
                )
            }
        }
    }

    private fun getLatestWeightEntries() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val resource = summaryUseCases.getLatestWeightEntries()) {
                is Resource.Success -> {
                    setWeightEntries(resource.data)
                }

                is Resource.Error -> {
                    showSnackbarError(message = resource.uiText)
                }
            }
        }
    }

    private fun setWeightEntries(weightEntries: List<WeightEntry>) {
        _state.update { state ->
            state.copy(
                weightEntries = weightEntries,
                weightProgress = summaryUseCases.calculateWeightProgress(weightEntries.toMutableList()),
                latestWeightEntry = weightEntries.sortedByDescending { it.timestamp }.getOrNull(0)
            )
        }
    }

    private fun initWantedCalories() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    wantedCalories = sharedPreferencesUtils.getWantedNutritionValues().calories
                )
            }
        }
    }

    private fun getLatestLogEntry() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    logStreak = sharedPreferencesUtils.getLatestLogEntry().streak
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