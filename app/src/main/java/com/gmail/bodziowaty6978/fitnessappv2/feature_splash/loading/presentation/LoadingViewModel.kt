package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.loading.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.NavigationAction
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository
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

    private val _loadingState = MutableStateFlow(LoadingState())

    private suspend fun checkNutritionValues(token: String) {
        val resource = loadingRepository.getNutritionValues(token)
        _loadingState.update {
            it.copy(
                hasNutritionValues = resource.data != null
            )
        }
        checkUser()
    }

    private fun checkUser() {
        var navigationAction: NavigationAction? = null
        _loadingState.value.hasToken?.let { hasToken ->
            if (hasToken) {
                val isLogged = _loadingState.value.isLoggedIn
                isLogged?.let {
                    if (isLogged) {
                        val hasNutrition = _loadingState.value.hasNutritionValues
                        if (hasNutrition != null) {
                            navigationAction = if (!hasNutrition) {
                                NavigationActions.LoadingScreen.loadingToIntroduction()
                            } else {
                                NavigationActions.LoadingScreen.loadingToSummary()
                            }
                        }
                    } else {
                        navigationAction = NavigationActions.LoadingScreen.loadingToLogin()
                    }
                }
            } else {
                navigationAction = NavigationActions.LoadingScreen.loadingToLogin()
            }
        }
        navigationAction?.let {
            navigator.navigate(it)
        }
    }

    fun checkIfTokenIsPresent() {
        viewModelScope.launch {
            var hasToken = false
            val token = getToken()
            token?.let { hasToken = true }
            _loadingState.update { it.copy(hasToken = hasToken) }
            if (hasToken) {
                authenticateUser(token!!)
                checkNutritionValues(token)
            }
            checkUser()
        }
    }

    private suspend fun authenticateUser(token: String) {
        _loadingState.update {
            it.copy(
                isLoggedIn = loadingRepository.authenticateUser(token).data != null
            )
        }
        checkUser()
    }
}