package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.presentation.register

import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.LogInUserUseCase
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case.RegisterUserUseCase

data class RegisterUseCases(
    val registerUserUseCase: RegisterUserUseCase,
    val logInUserUseCase: LogInUserUseCase
)