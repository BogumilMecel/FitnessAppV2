package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.loading.presentation

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.common.util.CustomDateUtils
import com.gmail.bogumilmecel2.fitnessappv2.destinations.IntroductionScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SummaryScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
    private val loadingRepository: LoadingRepository,
    private val getToken: GetToken,
) : BaseViewModel<Unit, Unit>(state = Unit) {

    fun checkIfTokenIsPresent() {
        viewModelScope.launch {
            getToken()?.let {
                authenticateUser()
            } ?: kotlin.run {
                navigateWithPopUp(
                    destination = LoginScreenDestination
                )
            }
        }
    }

    private suspend fun authenticateUser() {
        val authenticationResource = loadingRepository.authenticateUser(
            timezoneId = CustomDateUtils.getCurrentTimezoneId()
        )
        authenticationResource.data?.let { user ->
            cachedValuesProvider.saveUser(user = user)
            if (user.nutritionValues != null && user.userInformation != null) {
                navigateWithPopUp(
                    destination = SummaryScreenDestination
                )
            } else {
                navigateWithPopUp(
                    destination = IntroductionScreenDestination
                )
            }
        } ?: kotlin.run {
            navigateWithPopUp(
                destination = LoginScreenDestination
            )
        }
    }

    override fun onEvent(event: Unit) {}
}