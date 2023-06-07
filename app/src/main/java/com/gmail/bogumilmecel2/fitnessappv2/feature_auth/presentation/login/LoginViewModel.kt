package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.login

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RegisterScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ResetPasswordScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SplashScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.AuthUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.util.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : BaseViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EnteredEmail -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }

            is AuthEvent.EnteredPassword -> {
                _state.update {
                    it.copy(
                        password = event.password
                    )
                }
            }

            is AuthEvent.RegisterLoginButtonClicked -> {
                navigateTo(RegisterScreenDestination)
            }

            is AuthEvent.ForgotButtonClicked -> {
                navigateTo(ResetPasswordScreenDestination)
            }

            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                    with(_state.value) {
                        authUseCases.logInUser(
                            email = email,
                            password = password
                        ).handle {
                            navigateWithPopUp(
                                destination = SplashScreenDestination
                            )
                        }
                    }

                    _state.update {
                        it.copy(isLoading = false)
                    }
                }
            }
        }
    }
}