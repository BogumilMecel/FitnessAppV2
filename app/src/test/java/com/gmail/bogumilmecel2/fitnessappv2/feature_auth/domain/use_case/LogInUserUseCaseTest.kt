package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

import com.gmail.bogumilmecel2.auth.ValidateAuthDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.LoginRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.TokenResponse
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class LogInUserUseCaseTest: BaseTest() {

    private val authRepository = mockk<AuthRepository>()
    private val tokenRepository = mockk<TokenRepository>()
    private val validateAuthDataUseCase = mockk<ValidateAuthDataUseCase>()
    private val logInUserUseCase = LogInUserUseCase(
        authRepository = authRepository,
        resourceProvider = resourceProvider,
        tokenRepository = tokenRepository,
        validateAuthDataUseCase = validateAuthDataUseCase
    )

    @Test
    fun `Check if validation returns empty fields result, resource error is returned`() = runTest {
        mockValidationResult(result = ValidateAuthDataUseCase.Result.EmptyFields)
        callTestedMethod().assertIsError(resourceProvider.getString(R.string.empty_fields_error))
    }

    @Test
    fun `Check if validation returns invalid email length result, resource error is returned`() =
        runTest {
            mockValidationResult(result = ValidateAuthDataUseCase.Result.EmailLengthInvalid)
            callTestedMethod().assertIsError(resourceProvider.getString(R.string.register_email_length_invalid))
        }

    @Test
    fun `Check if validation returns invalid password length result, resource error is returned`() =
        runTest {
            mockValidationResult(result = ValidateAuthDataUseCase.Result.PasswordLengthInvalid)
            callTestedMethod().assertIsError(resourceProvider.getString(R.string.register_password_length_invalid))
        }

    @Test
    fun `Check if validation returns invalid email result, resource error is returned`() = runTest {
        mockValidationResult(result = ValidateAuthDataUseCase.Result.InvalidEmail)
        callTestedMethod().assertIsError(resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_email))
    }

    @Test
    fun `Check if validation returns success result and repository returns resource error, resource error is returned`() =
        runTest {
            mockValidationResult(result = ValidateAuthDataUseCase.Result.Success)
            mockRepositoryRequest(logInResource = Resource.Error())
            callTestedMethod().assertIsError()
            verifyRepositoryCall(tokenRepositoryExpected = 0)
        }

    @Test
    fun `Check if validation returns success result and auth repository returns resource success but token repository returns resource error, resource error is returned`() =
        runTest {
            mockValidationResult(result = ValidateAuthDataUseCase.Result.Success)
            mockRepositoryRequest(saveTokenResource = Resource.Error())
            callTestedMethod().assertIsError()
            verifyRepositoryCall(tokenRepositoryExpected = 1)
        }

    @Test
    fun `Check if validation returns success result, auth repository returns resource success and token repository returns resource success, resource success is returned`() =
        runTest {
            mockValidationResult(result = ValidateAuthDataUseCase.Result.Success)
            mockRepositoryRequest(saveTokenResource = Resource.Success(Unit))
            callTestedMethod().assertIsSuccess()
            verifyRepositoryCall(tokenRepositoryExpected = 1)
        }

    private fun mockRepositoryRequest(
        logInResource: Resource<TokenResponse> = Resource.Success(TokenResponse(token = MockConstants.Auth.TOKEN)),
        saveTokenResource: Resource<Unit> = Resource.Success(Unit)
    ) {
        coEvery {
            authRepository.logInUser(
                loginRequest = LoginRequest(
                    email = MockConstants.Auth.CORRECT_EMAIL,
                    password = MockConstants.Auth.CORRECT_PASSWORD
                )
            )
        } returns logInResource
        coEvery { tokenRepository.saveToken(token = MockConstants.Auth.TOKEN) } returns saveTokenResource
    }

    private fun verifyRepositoryCall(tokenRepositoryExpected: Int) {
        coVerify(exactly = 1) {
            authRepository.logInUser(
                loginRequest = LoginRequest(
                    email = MockConstants.Auth.CORRECT_EMAIL,
                    password = MockConstants.Auth.CORRECT_PASSWORD
                )
            )
        }
        coVerify(exactly = tokenRepositoryExpected) {
            tokenRepository.saveToken(token = MockConstants.Auth.TOKEN)
        }
    }

    private fun mockValidationResult(result: ValidateAuthDataUseCase.Result) {
        every {
            validateAuthDataUseCase.invoke(
                email = any(),
                password = any()
            )
        } returns result
    }

    private suspend fun callTestedMethod(
        email: String = MockConstants.Auth.CORRECT_EMAIL,
        password: String = MockConstants.Auth.CORRECT_PASSWORD,
    ) = logInUserUseCase(email = email, password = password)
}