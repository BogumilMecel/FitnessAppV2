package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.register

data class RegisterState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
)
