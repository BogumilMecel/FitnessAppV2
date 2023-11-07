package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.loading.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.OfflineMode
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util.BottomBarScreen
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.IntroductionScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SummaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.use_cases.AuthenticateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : BaseViewModel<Unit, Unit, Unit>(
    state = Unit,
    navArguments = Unit
) {

    override fun configureOnStart() {
        loaderVisible = true
        checkForOfflineMode()
    }

    private fun checkForOfflineMode() {
        viewModelScope.launch {
            cachedValuesProvider.setOfflineMode(
                offlineMode = when(connectivityObserver.isOnline()) {
                    true -> OfflineMode.Online
                    false -> OfflineMode.Offline
                }
            )
            authenticateUser()
        }
    }

    private suspend fun authenticateUser() {
        when (val result = authenticateUserUseCase()) {
            is AuthenticateUserUseCase.Result.NavigateToIntroduction -> navigateWithPopUp(IntroductionScreenDestination)
            is AuthenticateUserUseCase.Result.NavigateToLogin -> navigateWithPopUp(LoginScreenDestination)
            is AuthenticateUserUseCase.Result.NavigateToMainScreen -> {
                when (result.bottomBarScreen) {
                    BottomBarScreen.Summary -> navigateWithPopUp(SummaryScreenDestination)
                    else -> {
                        // TODO: Implement it when setting default starting screen is implemented
                    }
                }
            }
        }
    }

    override fun onEvent(event: Unit) {}
}