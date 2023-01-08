package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.navigation.NavigationActions
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.navigation.Navigator
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.TextFieldState
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case.AuthUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.util.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val navigator: Navigator,
    resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _emailState = mutableStateOf<TextFieldState>(
        TextFieldState(
            hint = resourceProvider.getString(R.string.email_address)
        )
    )
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf<TextFieldState>(
        TextFieldState(
            hint = resourceProvider.getString(R.string.password)
        )
    )
    val passwordState: State<TextFieldState> = _passwordState

    private val _isLoading = mutableStateOf<Boolean>(false)
    val isLoading: State<Boolean> = _isLoading

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
                navigator.navigate(NavigationActions.LoginScreen.loginToRegister())
            }

            is AuthEvent.ForgotButtonClicked -> {
                navigator.navigate(NavigationActions.LoginScreen.loginToReset())
            }

            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.value = true
                    val resource = authUseCases.logInUser(
                        email = _emailState.value.text,
                        password = _passwordState.value.text
                    )
                    if (resource is CustomResult.Error) {
                        showSnackbarError(resource.message)
                    } else {
                        navigator.navigate(NavigationActions.LoginScreen.loginToLoading())
                    }
                    _isLoading.value = false
                }
            }
        }
    }

}