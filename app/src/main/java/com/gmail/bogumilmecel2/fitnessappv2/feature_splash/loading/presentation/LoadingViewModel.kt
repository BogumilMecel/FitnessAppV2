package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.loading.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.IntroductionScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SummaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.repository.DiaryRepository
import com.gmail.bogumilmecel2.fitnessappv2.feature_diary.domain.use_cases.GetUserDiaryAndSaveItLocallyUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val loadingRepository: LoadingRepository,
    private val getToken: GetToken,
    private val getUserDiaryAndSaveItLocallyUseCase: GetUserDiaryAndSaveItLocallyUseCase,
    private val diaryRepository: DiaryRepository
) : BaseViewModel<Unit, Unit>(state = Unit) {

    fun checkIfTokenIsPresent() {
        viewModelScope.launch(Dispatchers.IO) {
            getToken()?.let {
                authenticateUser()
            } ?: run {
                navigateToLoginScreen()
            }
        }
    }

    private suspend fun authenticateUser() {
        loadingRepository.authenticateUser().handle(
            onError = { navigateToLoginScreen() }
        ) { user ->
            when {
                user == null -> {
                    navigateToLoginScreen()
                }

                user.nutritionValues != null && user.userInformation != null -> {
                    cachedValuesProvider.saveUser(user = user)
                    diaryRepository.clearLocalData()
                    getUserDiaryAndSaveItLocallyUseCase()
                    navigateWithPopUp(destination = SummaryScreenDestination)
                }

                else -> {
                    cachedValuesProvider.saveUser(user = user)
                    navigateWithPopUp(destination = IntroductionScreenDestination)
                }
            }
        }
    }

    private fun navigateToLoginScreen() {
        navigateWithPopUp(destination = LoginScreenDestination)
    }

    override fun onEvent(event: Unit) {}
}