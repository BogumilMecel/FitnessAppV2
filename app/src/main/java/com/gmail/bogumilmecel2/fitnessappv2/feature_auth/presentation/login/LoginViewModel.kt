package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.login

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RegisterScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ResetPasswordScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SplashScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.LogInUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val logInUserUseCase: LogInUserUseCase,
) : BaseViewModel<LoginState, LoginEvent, Unit>(
    state = LoginState(),
    navArguments = Unit
) {

    override fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.EnteredEmail -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is LoginEvent.EnteredPassword -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            is LoginEvent.RegisterLoginButtonClicked -> {
                navigateTo(RegisterScreenDestination)
            }

            is LoginEvent.ForgotButtonClicked -> {
                navigateTo(ResetPasswordScreenDestination)
            }

            is LoginEvent.LoginButtonClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    loaderVisible = true
                    with(_state.value) {
                        logInUserUseCase(
                            email = email,
                            password = password
                        ).handle {
                            navigateWithPopUp(
                                destination = SplashScreenDestination
                            )
                        }
                    }
                    loaderVisible = false
                }
            }
        }
    }
}