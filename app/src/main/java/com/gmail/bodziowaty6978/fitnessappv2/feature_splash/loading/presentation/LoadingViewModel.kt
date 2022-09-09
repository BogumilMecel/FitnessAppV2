package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.loading.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bodziowaty6978.fitnessappv2.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val loadingRepository: LoadingRepository,
    private val getToken: GetToken,
    private val navigator: Navigator
) : ViewModel() {

    private val _loadingState = MutableStateFlow<LoadingState>(LoadingState())

    fun onEvent(event: LoadingEvent) {
        Log.e(TAG, event.toString())
        when (event) {
            is LoadingEvent.ReceivedNutrition -> {
                _loadingState.value = _loadingState.value.copy(
                    hasNutritionValues = event.nutritionValues.calories != -1
                )
                checkUser()
            }
            is LoadingEvent.ReceivedInformation -> {
                _loadingState.value = _loadingState.value.copy(
                    hasInformation = event.userInformation.age != -1
                )
                checkUser()
            }
        }
    }

    private fun checkUser() {
        val hasToken = _loadingState.value.hasToken
        hasToken?.let {
            Log.e(TAG,hasToken.toString())
            if (it){
                val isLogged = _loadingState.value.isLoggedIn
                isLogged?.let {
                    Log.e(TAG, "is logged = $isLogged")
                    if (isLogged) {
                        val hasInformation = _loadingState.value.hasInformation
                        val hasNutrition = _loadingState.value.hasNutritionValues
                        if (hasInformation != null && hasNutrition != null) {
                            if (!hasInformation || !hasNutrition) {
                                navigator.navigate(NavigationActions.LoadingScreen.loadingToIntroduction())
                            } else {
                                navigator.navigate(NavigationActions.LoadingScreen.loadingToSummary())
                            }
                        }

                    } else {
                        navigator.navigate(NavigationActions.LoadingScreen.loadingToLogin())
                    }
                }
            }else{
                navigator.navigate(NavigationActions.LoadingScreen.loadingToLogin())
            }
        }
    }

    fun checkIfTokenIsPresent() {
        viewModelScope.launch {
            getToken()?.let {token ->
                _loadingState.update {
                    it.copy(
                        hasToken = true
                    )
                }
                authenticateUser(token)
            } ?: kotlin.run {
                _loadingState.update {
                    it.copy(
                        hasToken = false
                    )
                }
            }
            checkUser()
        }
    }

    private fun authenticateUser(
        token: String
    ) {
        viewModelScope.launch {
            val resource = loadingRepository.authenticateUser(token)
            Log.e(TAG,resource.toString())
            if (resource is Resource.Error) {
                _loadingState.update {
                    it.copy(
                        isLoggedIn = false
                    )
                }
            } else {
                if (resource.data != null) {
                    _loadingState.update {
                        it.copy(
                            isLoggedIn = true
                        )
                    }
                } else {
                    _loadingState.update {
                        it.copy(
                            isLoggedIn = false
                        )
                    }
                }
            }
            checkUser()
        }
    }
}