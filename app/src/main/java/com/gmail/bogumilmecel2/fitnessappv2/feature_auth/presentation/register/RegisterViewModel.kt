package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.register

import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.LoginScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SplashScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.AuthUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.util.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
): BaseViewModel<RegisterState, AuthEvent>(state = RegisterState()){

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
                    _state.update {
                        it.copy(
                            isLoading = true
                        )
                    }
                    with(_state.value) {
                        authUseCases.registerUser(
                            email = email,
                            password = password,
                            confirmPassword = confirmPassword,
                            username = username
                        ).handle {
                            navigateWithPopUp(
                                destination = SplashScreenDestination
                            )
                        }
                    }

                    _state.update {
                        it.copy(
                            isLoading = false
                        )
                    }
                }
            }
            else -> {
                navigateTo(LoginScreenDestination)
            }
        }
    }

}