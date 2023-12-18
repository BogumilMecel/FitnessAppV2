package com.gmail.bodziowaty6978.fitnessappv2.feature_summary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetWantedNutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.TAG
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.use_case.SummaryUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.SummaryEvent
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.SummaryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val summaryUseCases: SummaryUseCases,
    private val resourceProvider: ResourceProvider,
    private val getWantedNutritionValues: GetWantedNutritionValues
) : ViewModel() {

    private val _errorState = Channel<String>()
    val errorState = _errorState.receiveAsFlow()

    private val _state = MutableStateFlow(SummaryState())
    val summaryState: StateFlow<SummaryState> = _state

    init {
        getLatestLogEntry()
        getCaloriesSum()
        getLatestWeightEntries()
        initWantedCalories()
    }

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

    private fun saveNewWeightEntry(value: Double) {
        viewModelScope.launch {
            val resource = summaryUseCases.addWeightEntry(
                value = value
            )
            when (resource) {
                is Resource.Success -> {
                    resource.data?.let { newEntry ->
                        _state.update { state ->
                            val newEntriesList = state.weightEntries.toMutableList().apply {
                                add(newEntry)
                                toList()
                            }
                            Log.e(TAG, newEntriesList.toString())
                            state.copy(
                                isWeightPickerVisible = false,
                                weightEntries = newEntriesList,
                                weightProgress = summaryUseCases.calculateWeightProgress(
                                    weightEntries = newEntriesList
                                )
                            )
                        }
                        Log.e(TAG, newEntry.toString())
                        Log.e(TAG, _state.value.weightProgress.toString())
                    }
                }

                is Resource.Error -> {

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
                    resource.data?.let { entries ->
                        if (entries.isNotEmpty()) {
                            _state.update { state ->
                                state.copy(
                                    weightEntries = entries,
                                    weightProgress = summaryUseCases.calculateWeightProgress(entries.toMutableList())
                                )
                            }
                        }

                    }
                }

                is Resource.Error -> {
                    resource.uiText?.let {
                        _errorState.send(it)
                    }
                }
            }
        }
    }

    private fun initWantedCalories() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    wantedCalories = getWantedNutritionValues().calories
                )
            }
        }

    }

    private fun getLatestLogEntry() {
        viewModelScope.launch(Dispatchers.IO) {
            var logStreak = 1
            val resource = summaryUseCases.getLatestLogEntry()
            if (resource is Resource.Success) {
                resource.data?.let { logEntry ->
                    val checkResource = summaryUseCases.checkLatestLogEntry(
                        logEntry = logEntry
                    )
                    logStreak =
                        if (checkResource.data != null && checkResource is Resource.Success) {
                            checkResource.data.streak
                        } else 1
                }
            }
            _state.update {
                it.copy(
                    logStreak = logStreak
                )
            }
        }
    }

    private fun getCaloriesSum() {
        viewModelScope.launch {
            val resource = summaryUseCases.getCaloriesSum(
                date = CurrentDate.dateModel(
                    resourceProvider = resourceProvider
                ).date
            )
            Log.e(TAG, resource.toString())
            var caloriesSum = 0
            if (resource is Resource.Success) {
                resource.data?.let { sum ->
                    caloriesSum = sum
                }
            }
            _state.update {
                it.copy(
                    caloriesSum = caloriesSum
                )
            }
        }
    }
}