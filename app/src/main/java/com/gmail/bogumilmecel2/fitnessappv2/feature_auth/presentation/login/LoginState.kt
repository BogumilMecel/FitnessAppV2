package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)