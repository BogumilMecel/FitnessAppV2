package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.loading.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.extensions.TAG
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.AuthenticationRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val loadingRepository: LoadingRepository,
    private val getToken: GetToken,
) : BaseViewModel() {

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
        val authenticationResource = loadingRepository.authenticateUser(
            authenticationRequest = AuthenticationRequest(timestamp = System.currentTimeMillis())
        )
        Log.e(TAG, authenticationResource.toString())
        authenticationResource.data?.let { user ->
            if (user.nutritionValues != null && user.userInformation != null) {
                navigate(NavigationActions.LoadingScreen.loadingToSummary())
            } else {
                navigate(NavigationActions.LoadingScreen.loadingToIntroduction())
            }
        } ?: kotlin.run {
            navigate(NavigationActions.LoadingScreen.loadingToLogin())
        }
    }
}