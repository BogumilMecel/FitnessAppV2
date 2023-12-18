package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.TextFieldState
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.destinations.LoginScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.destinations.SplashScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case.AuthUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.util.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    resourceProvider: ResourceProvider
): BaseViewModel(){

    private val _emailState = mutableStateOf<TextFieldState>(TextFieldState(
        hint = resourceProvider.getString(R.string.email_address)
    ))
    val emailState: State<TextFieldState> = _emailState

    private val _passwordState = mutableStateOf<TextFieldState>(TextFieldState(
        hint = resourceProvider.getString(R.string.password)
    ))
    val passwordState: State<TextFieldState> = _passwordState

    private val _confirmPasswordState = mutableStateOf<TextFieldState>(TextFieldState(
        hint = resourceProvider.getString(R.string.confirm_your_password)
    ))
    val confirmPasswordState: State<TextFieldState> = _confirmPasswordState

    private val _usernameState = mutableStateOf<TextFieldState>(TextFieldState(
        hint = resourceProvider.getString(R.string.username)
    ))
    val usernameState: State<TextFieldState> = _usernameState

    private val _isLoading = mutableStateOf<Boolean>(false)
    val isLoading: State<Boolean> = _isLoading

    fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.EnteredEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.email,
                )
            }
            is AuthEvent.EnteredUsername -> {
                _usernameState.value = usernameState.value.copy(
                    text = event.username,
                )
            }
            is AuthEvent.EnteredPassword -> {
                _passwordState.value = passwordState.value.copy(
                    text = event.password,
                )
            }
            is AuthEvent.EnteredConfirmPassword -> {
                _confirmPasswordState.value = confirmPasswordState.value.copy(
                    text = event.confirmPassword,
                )
            }
            is AuthEvent.AuthButtonClicked -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.value = true
                    val result = authUseCases.registerUser(
                        email = _emailState.value.text,
                        password = _passwordState.value.text,
                        confirmPassword = _confirmPasswordState.value.text,
                        username = _usernameState.value.text
                    )
                    if (result is CustomResult.Error){
                        showSnackbarError(result.message)
                    }else{
                        navigateTo(
                            destination = SplashScreenDestination,
                            navOptions = NavOptions.Builder().setPopUpTo(0, true).build()
                        )
                    }
                    _isLoading.value = false
                }
            }
            else -> {
                navigateTo(LoginScreenDestination)
            }
        }
    }

}