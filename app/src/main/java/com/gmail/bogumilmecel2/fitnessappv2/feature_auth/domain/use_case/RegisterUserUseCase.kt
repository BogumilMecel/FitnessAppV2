package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

import com.gmail.bogumilmecel2.auth.ValidateRegisterDataUseCase
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.RegisterRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository

class RegisterUserUseCase(
    private val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider,
    private val validateRegisterDataUseCase: ValidateRegisterDataUseCase
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String,
        username: String
    ): Resource<Unit> {
        if (confirmPassword != password) return Resource.Error(resourceProvider.getString(R.string.please_make_sure_both_passwords_are_the_same))

        validateRegisterDataUseCase(
            username = username,
            email = email,
            password = password
        ).run {
            println(this)
            return when (this) {
                ValidateRegisterDataUseCase.Result.EmailLengthInvalid ->
                    Resource.Error(resourceProvider.getString(R.string.register_email_length_invalid))

                ValidateRegisterDataUseCase.Result.PasswordLengthInvalid ->
                    Resource.Error(resourceProvider.getString(R.string.please_make_sure_your_password_is_at_least_6_characters_and_is_not_longer_than_24_characters))

                ValidateRegisterDataUseCase.Result.UsernameLengthInvalid ->
                    Resource.Error(resourceProvider.getString(R.string.register_username_length_invalid))

                ValidateRegisterDataUseCase.Result.InvalidEmail ->
                    Resource.Error(resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_email))

                ValidateRegisterDataUseCase.Result.Success -> authRepository.registerUser(
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