package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

// TODO: Delete and separate use cases to specific screen
data class AuthUseCases(
    val logInUserUseCase: LogInUserUseCase,
    val registerUser: RegisterUser,
    val resetPasswordWithEmail: ResetPasswordWithEmail
)