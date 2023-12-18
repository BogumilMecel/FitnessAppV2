package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.loading.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.FitnessApp
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.use_case.GetLatestLogEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val loadingRepository: LoadingRepository,
    private val getToken: GetToken,
    private val getLatestLogEntry: GetLatestLogEntry
) : BaseViewModel() {

    private suspend fun checkUser() {
        loadingRepository.getUser().data?.let {
            if (it.nutritionValues != null && it.userInformation != null) {
                requestLatestLogEntry()
            } else {
                navigate(NavigationActions.LoadingScreen.loadingToIntroduction())
            }
        } ?: kotlin.run {
            navigate(NavigationActions.LoadingScreen.loadingToLogin())
        }
    }

    private suspend fun requestLatestLogEntry() {
        getLatestLogEntry(logRequest = LogRequest(timestamp = System.currentTimeMillis())).data?.let {
            FitnessApp.updateLatestLogEntry(it)
            navigate(NavigationActions.LoadingScreen.loadingToSummary())
        }
    }

    fun checkIfTokenIsPresent() {
        viewModelScope.launch {
            getToken()?.let {
                authenticateUser()
            } ?: kotlin.run {
                navigate(NavigationActions.LoadingScreen.loadingToLogin())
            }
        }
    }

    private suspend fun authenticateUser() {
        if (loadingRepository.authenticateUser() is CustomResult.Success) {
            checkUser()
        } else {
            navigate(NavigationActions.LoadingScreen.loadingToLogin())
        }
    }
}