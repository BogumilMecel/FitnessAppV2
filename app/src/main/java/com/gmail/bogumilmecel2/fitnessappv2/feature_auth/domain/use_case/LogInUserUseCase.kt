package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

import android.util.Patterns
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.repository.TokenRepository
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.LoginRequest
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository

class LogInUserUseCase(
    private val authRepository: AuthRepository,
    private val resourceProvider: ResourceProvider,
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(email: String, password: String): Resource<Unit> {
        if (email.isBlank() || password.isBlank()) {
            return Resource.Error(resourceProvider.getString(R.string.please_make_sure_all_fields_are_filled_in_correctly))
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Resource.Error(resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_email))
        }
        val resource = authRepository.logInUser(
            loginRequest = LoginRequest(
                email = email,
                password = password
            )
        )

        return when(resource) {
            is Resource.Success -> {
                tokenRepository.saveToken(resource.data.token)
            }

            is Resource.Error -> {
                Resource.Error(resource.uiText)
            }
        }
    }
}