package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

import com.gmail.bogumilmecel2.auth.ValidateAuthDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.LoginRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository

class LogInUserUseCase(
    private val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider,
    private val tokenRepository: TokenRepository,
    private val validateAuthDataUseCase: ValidateAuthDataUseCase
) {

    suspend operator fun invoke(email: String, password: String): Resource<Unit> {
        val validationResult = validateAuthDataUseCase(email = email, password = password)

        when (validationResult) {
            ValidateAuthDataUseCase.Result.EmptyFields ->
                return Resource.Error(resourceProvider.getString(R.string.empty_fields_error))

            ValidateAuthDataUseCase.Result.EmailLengthInvalid ->
                return Resource.Error(resourceProvider.getString(R.string.register_email_length_invalid))

            ValidateAuthDataUseCase.Result.PasswordLengthInvalid ->
                return Resource.Error(resourceProvider.getString(R.string.register_password_length_invalid))

            ValidateAuthDataUseCase.Result.InvalidEmail ->
                return Resource.Error(resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_email))

            ValidateAuthDataUseCase.Result.Success -> {
                val resource = authRepository.logInUser(
                    loginRequest = LoginRequest(
                        email = email,
                        password = password
                    )
                )

                return when (resource) {
                    is Resource.Success -> tokenRepository.saveToken(resource.data.token)
                    is Resource.Error -> Resource.Error(resource.uiText)
                }
            }

            else -> return Resource.Error()
        }
    }
}