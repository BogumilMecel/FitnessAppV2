package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.reset_password

data class ResetPasswordState(
    val email: String = "",
    val isLoading: Boolean = false
)
