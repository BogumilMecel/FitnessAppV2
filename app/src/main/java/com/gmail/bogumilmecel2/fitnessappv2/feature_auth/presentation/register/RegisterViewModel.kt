package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.register

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SplashScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.util.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val useCases: RegisterUseCases
): BaseViewModel<RegisterState, AuthEvent, Unit>(
    state = RegisterState(),
    navArguments = Unit
){

    override fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.EnteredEmail -> {
                _state.update {
                    it.copy(
                        email = event.email
                    )
                }
            }
            is AuthEvent.EnteredUsername -> {
                _state.update {
                    it.copy(
                        username = event.username
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
            is AuthEvent.EnteredConfirmPassword -> {
                _state.update {
                    it.copy(
                        confirmPassword = event.confirmPassword
                    )
                }
            }
            is AuthEvent.AuthButtonClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    loaderVisible = true
                    with(_state.value) {
                        useCases.registerUserUseCase(
                            email = email,
                            password = password,
                            confirmPassword = confirmPassword,
                            username = username
                        ).handle {
                            useCases.logInUserUseCase(
                                email = email,
                                password = password
                            ).handle {
                                navigateWithPopUp(
                                    destination = SplashScreenDestination
                                )
                            }
                        }
                    }
                    loaderVisible = false
                }
            }
            else -> {
                navigateTo(LoginScreenDestination)
            }
        }
    }
}