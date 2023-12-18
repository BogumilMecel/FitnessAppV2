package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.presentation.components.TextFieldState
import com.gmail.bogumilmecel2.fitnessappv2.common.util.BaseViewModel
import com.gmail.bogumilmecel2.fitnessappv2.destinations.RegisterScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.ResetPasswordScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.destinations.SplashScreenDestination
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.AuthUseCases
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.util.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : BaseViewModel() {

    private val _emailState = mutableStateOf(TextFieldState())
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf(TextFieldState())
    val passwordState: State<TextFieldState> = _passwordState

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    init {
        initializeHints()
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EnteredEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.email,
                )
            }

            is AuthEvent.EnteredPassword -> {
                _passwordState.value = passwordState.value.copy(
                    text = event.password,
                )
            }

            is AuthEvent.RegisterLoginButtonClicked -> {
                navigateTo(RegisterScreenDestination)
            }

            is AuthEvent.ForgotButtonClicked -> {
                navigateTo(ResetPasswordScreenDestination)
            }

            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.value = true
                    authUseCases.logInUser(
                        email = _emailState.value.text,
                        password = _passwordState.value.text
                    ).handle {
                        navigateWithPopUp(
                            destination = SplashScreenDestination
                        )
                    }
                    _isLoading.value = false
                }
            }
        }
    }

    private fun initializeHints() {
        _passwordState.value = TextFieldState(hint = resourceProvider.getString(R.string.password))
        _emailState.value = TextFieldState(hint = resourceProvider.getString(R.string.email_address))
    }
}