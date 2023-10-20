package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

import com.gmail.bogumilmecel2.auth.ValidateRegisterDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseMockkTest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import io.mockk.mockk
import io.mockk.mockkClass

internal class RegisterUserUseCaseTest: BaseMockkTest() {

    private val authRepository = mockk<AuthRepository>()
    private val validateRegisterDataUseCase = mockkClass(ValidateRegisterDataUseCase::class)
    private val registerUserUseCase = RegisterUserUseCase(
        authRepository = authRepository,
        resourceProvider = resourceProvider,
        validateRegisterDataUseCase = validateRegisterDataUseCase
    )

}

