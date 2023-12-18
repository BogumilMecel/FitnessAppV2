package com.gmail.bodziowaty6978.fitnessappv2.feature_summary

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.singleton.CurrentDate
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.use_case.SummaryUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.presentation.SummaryState
import com.gmail.bodziowaty6978.fitnessappv2.util.TAG
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
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val _errorState = Channel<String>()
    val errorState = _errorState.receiveAsFlow()

    private val _summaryState = MutableStateFlow(SummaryState())
    val summaryState: StateFlow<SummaryState> = _summaryState

    init {
        getLatestLogEntry()
        getCaloriesSum()
    }

    private fun getLatestLogEntry() {
        viewModelScope.launch(Dispatchers.IO) {
            var logStreak = 1
            when (val resource = summaryUseCases.getLatestLogEntry()) {
                is Resource.Error -> {
                    logStreak = 1
                }
                is Resource.Success -> {
                    resource.data?.let { logEntry ->
                        val checkResource = summaryUseCases.checkLatestLogEntry(
                            logEntry = logEntry
                        )
                        logStreak = if (checkResource.data != null && checkResource is Resource.Success) {
                                checkResource.data.streak
                            } else 1
                    }
                }
            }
            _summaryState.update {
                it.copy(
                    logStreak = logStreak
                )
            }
        }
    }

    private fun getCaloriesSum(){
        viewModelScope.launch {
            val resource = summaryUseCases.getCaloriesSum(
                date = CurrentDate.dateModel(
                    resourceProvider = resourceProvider
                ).date
            )
            Log.e(TAG,resource.toString())
            var caloriesSum = 0
            if (resource is Resource.Success){
                resource.data?.let { sum ->
                    caloriesSum = sum
                }
            }
            _summaryState.update {
                it.copy(
                    caloriesSum = caloriesSum
                )
            }
        }
    }
}