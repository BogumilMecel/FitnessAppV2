package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

data class AuthUseCases(
    val logInUser: LogInUser,
    val registerUser: RegisterUser,
    val resetPasswordWithEmail: ResetPasswordWithEmail
)