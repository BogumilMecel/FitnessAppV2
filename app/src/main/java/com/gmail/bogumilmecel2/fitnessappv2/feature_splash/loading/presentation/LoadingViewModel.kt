package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.loading.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.util.BottomBarScreen
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.IntroductionScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SummaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.LoadingUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.use_cases.AuthenticateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val useCases: LoadingUseCases
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
            checkConnectionStateUseCase()
            authenticateUser()
        }
    }

    private suspend fun authenticateUser() {
        when (val result = useCases.authenticateUserUseCase()) {
            is AuthenticateUserUseCase.Result.NavigateToIntroduction -> navigateWithPopUp(IntroductionScreenDestination)
            is AuthenticateUserUseCase.Result.NavigateToLogin -> navigateWithPopUp(LoginScreenDestination)
            is AuthenticateUserUseCase.Result.NavigateToMainScreen -> {

                viewModelScope.launch(Dispatchers.IO) {
                    val userProductsJob = async { useCases.getProductsAndSaveOfflineUseCase() }
                    val userRecipesJob = async { useCases.getRecipesAndSaveOfflineUseCase() }
                    val userProductDiaryEntriesJob = async { useCases.getProductDiaryAndSaveOfflineUseCase() }
                    val userRecipeDiaryEntriesJob = async { useCases.getRecipeDiaryAndSaveOfflineUseCase() }

                    awaitAll(userProductsJob, userRecipesJob, userProductDiaryEntriesJob, userRecipeDiaryEntriesJob)
                }

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