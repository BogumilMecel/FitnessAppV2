package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

import com.gmail.bogumilmecel2.auth.ValidateAuthDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.RegisterRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository

class RegisterUserUseCase(
    private val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider,
    private val validateAuthDataUseCase: ValidateAuthDataUseCase
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String,
        username: String
    ): Resource<Unit> {
        if (confirmPassword != password) return Resource.Error(resourceProvider.getString(R.string.please_make_sure_both_passwords_are_the_same))

        validateAuthDataUseCase(
            username = username,
            email = email,
            password = password
        ).run {
            println(this)
            return when (this) {
                ValidateAuthDataUseCase.Result.EmptyFields ->
                    Resource.Error(resourceProvider.getString(R.string.please_make_sure_all_fields_are_filled_in_correctly))

                ValidateAuthDataUseCase.Result.EmailLengthInvalid ->
                    Resource.Error(resourceProvider.getString(R.string.register_email_length_invalid))

                ValidateAuthDataUseCase.Result.PasswordLengthInvalid ->
                    Resource.Error(resourceProvider.getString(R.string.please_make_sure_your_password_is_at_least_6_characters_and_is_not_longer_than_24_characters))

                ValidateAuthDataUseCase.Result.UsernameLengthInvalid ->
                    Resource.Error(resourceProvider.getString(R.string.register_username_length_invalid))

                ValidateAuthDataUseCase.Result.InvalidEmail ->
                    Resource.Error(resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_email))

                ValidateAuthDataUseCase.Result.Success -> authRepository.registerUser(
                    registerRequest = RegisterRequest(
                        username = username,
                        email = email,
                        password = password
                    )
                )
            }
        }
    }
}