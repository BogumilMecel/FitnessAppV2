package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

import com.gmail.bogumilmecel2.auth.ValidateAuthDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.BaseTest
import com.gmail.bogumilmecel2.fitnessappv2.common.MockConstants
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.RegisterRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.test.runTest
import org.junit.Test

internal class RegisterUserUseCaseTest : BaseTest() {

    private val authRepository = mockk<AuthRepository>()
    private val validateAuthDataUseCase = mockkClass(ValidateAuthDataUseCase::class)
    private val registerUserUseCase = RegisterUserUseCase(
        authRepository = authRepository,
        resourceProvider = resourceProvider,
        validateAuthDataUseCase = validateAuthDataUseCase
    )

    @Test
    fun `Check if passwords are not matching resource error is returned`() = runTest {
        callTestedMethod(confirmPassword = MockConstants.Auth.CORRECT_PASSWORD_2)
            .assertIsError(resourceProvider.getString(R.string.please_make_sure_both_passwords_are_the_same))
    }

    @Test
    fun `Check if validation returns empty fields result, resource error is returned`() = runTest {
        mockValidationResult(result = ValidateAuthDataUseCase.Result.EmptyFields)
        callTestedMethod().assertIsError(resourceProvider.getString(R.string.empty_fields_error))
    }

    @Test
    fun `Check if validation returns invalid email result, resource error is returned`() = runTest {
        mockValidationResult(result = ValidateAuthDataUseCase.Result.InvalidEmail)
        callTestedMethod().assertIsError(resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_email))
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
    fun `Check if validation returns invalid username length result, resource error is returned`() =
        runTest {
            mockValidationResult(result = ValidateAuthDataUseCase.Result.UsernameLengthInvalid)
            callTestedMethod().assertIsError(resourceProvider.getString(R.string.register_username_length_invalid))
        }

    @Test
    fun `Check if validation returns success result and repository returns resource error, resource error is returned`() =
        runTest {
            mockValidationResult(result = ValidateAuthDataUseCase.Result.Success)
            mockRepositoryRequest(Resource.Error())
            callTestedMethod().assertIsError()
            verifyRepositoryCall()
        }

    @Test
    fun `Check if validation returns success result and repository returns resource success, resource success is returned`() =
        runTest {
            mockValidationResult(result = ValidateAuthDataUseCase.Result.Success)
            mockRepositoryRequest()
            callTestedMethod().assertIsSuccess()
            verifyRepositoryCall()
        }

    private fun verifyRepositoryCall() {
        coVerify(exactly = 1) {
            authRepository.registerUser(
                registerRequest = RegisterRequest(
                    username = MockConstants.Auth.CORRECT_USERNAME,
                    email = MockConstants.Auth.CORRECT_EMAIL,
                    password = MockConstants.Auth.CORRECT_PASSWORD
                )
            )
        }
    }

    private fun mockRepositoryRequest(resource: Resource<Unit> = Resource.Success(Unit)) {
        coEvery {
            authRepository.registerUser(
                registerRequest = RegisterRequest(
                    username = MockConstants.Auth.CORRECT_USERNAME,
                    email = MockConstants.Auth.CORRECT_EMAIL,
                    password = MockConstants.Auth.CORRECT_PASSWORD
                )
            )
        } returns resource
    }

    private fun mockValidationResult(result: ValidateAuthDataUseCase.Result) {
        every {
            validateAuthDataUseCase.invoke(
                username = any(),
                email = any(),
                password = any()
            )
        } returns result
    }

    private suspend fun callTestedMethod(
        email: String = MockConstants.Auth.CORRECT_EMAIL,
        password: String = MockConstants.Auth.CORRECT_PASSWORD,
        confirmPassword: String = MockConstants.Auth.CORRECT_PASSWORD,
        username: String = MockConstants.Auth.CORRECT_USERNAME
    ) = registerUserUseCase(
        email = email,
        password = password,
        confirmPassword = confirmPassword,
        username = username
    )
}

