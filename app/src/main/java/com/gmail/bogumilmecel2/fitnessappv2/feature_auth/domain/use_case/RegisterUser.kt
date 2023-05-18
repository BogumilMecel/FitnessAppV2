package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

import android.util.Patterns
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.common.util.RealResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.RegisterRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository

class RegisterUser(
    private val repository: AuthRepository,
    private val realResourceProvider: RealResourceProvider,
) {

    suspend operator fun invoke(
        email: String,
        password: String,
        confirmPassword: String,
        username: String
    ): Resource<Boolean> {
        if (email.isBlank() || password.isBlank() || username.isBlank() || confirmPassword.isBlank()) {
            return Resource.Error(realResourceProvider.getString(R.string.please_make_sure_all_fields_are_filled_in_correctly))
        }
        if (confirmPassword != password) {
            return Resource.Error(realResourceProvider.getString(R.string.please_make_sure_both_passwords_are_the_same))
        }
        if (password.length < 6 || password.length > 24) {
            return Resource.Error(realResourceProvider.getString(R.string.please_make_sure_your_password_is_at_least_6_characters_and_is_not_longer_than_24_characters))
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Resource.Error(realResourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_email))
        }

        return repository.registerUser(
            registerRequest = RegisterRequest(
                username = username,
                email = email,
                password = password
            )
        )
    }
}