package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.reset_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.presentation.components.TextFieldState
import com.gmail.bodziowaty6978.fitnessappv2.common.util.BaseViewModel
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.destinations.LoginScreenDestination
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case.AuthUseCases
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.presentation.util.AuthEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    resourceProvider: ResourceProvider
) : BaseViewModel() {

    private val _emailState = mutableStateOf<TextFieldState>(
        TextFieldState(
            hint = resourceProvider.getString(R.string.email_address)
        )
    )
    val emailState: State<TextFieldState> = _emailState

    private val _isLoading = mutableStateOf<Boolean>(false)
    val isLoading: State<Boolean> = _isLoading

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.EnteredEmail -> {
                _emailState.value = emailState.value.copy(
                    text = event.email,
                )
            }

            is AuthEvent.RegisterLoginButtonClicked -> {
                navigateTo(LoginScreenDestination)
            }

            else -> {
                viewModelScope.launch(Dispatchers.IO) {
                    _isLoading.value = true
                    val result = authUseCases.resetPasswordWithEmail(
                        email = _emailState.value.text
                    )
                    when (result) {
                        is CustomResult.Success -> {
                            showSnackbarError("Successfully sent an email")
                        }

                        is CustomResult.Error -> {
                            showSnackbarError(result.message)
                        }
                    }
                    _isLoading.value = false
                }
            }
        }
    }

}