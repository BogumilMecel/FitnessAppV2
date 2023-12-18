package com.gmail.bodziowaty6978.fitnessappv2.feature_summary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.use_case.LogUseCases
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
    private val logUseCases: LogUseCases
) : ViewModel() {

    private val _errorState = Channel<String>()
    val errorState = _errorState.receiveAsFlow()

    private val _summaryState = MutableStateFlow(SummaryState())
    val summaryState: StateFlow<SummaryState> = _summaryState

    init {
        getLatestLogEntry()
    }

    private fun getLatestLogEntry() {
        viewModelScope.launch(Dispatchers.IO) {
            var logStreak = 1
            when (val resource = logUseCases.getLatestLogEntry()) {
                is Resource.Error -> {
                    logStreak = 1
                }
                is Resource.Success -> {
                    resource.data?.let { logEntry ->
                        val checkResource = logUseCases.checkLatestLogEntry(
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
}