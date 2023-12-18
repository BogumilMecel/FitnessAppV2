package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.use_case

import android.util.Patterns
import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.SaveToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.repository.AuthRepository

class LogInUser(
    private val repository: AuthRepository,
    private val resourceProvider: ResourceProvider,
    private val saveToken: SaveToken
) {

    suspend operator fun invoke(email: String, password: String): CustomResult {
        if (email.isBlank() || password.isBlank()) {
            return CustomResult.Error(resourceProvider.getString(R.string.please_make_sure_all_fields_are_filled_in_correctly))
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return CustomResult.Error(resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_email))
        }
        val loginResource = repository.logInUser(
            email = email,
            password = password
        )
        return when (loginResource) {
            is Resource.Success -> {
                loginResource.data?.let {
                    if (saveToken(it.token)) CustomResult.Success else CustomResult.Error(
                        resourceProvider.getString(R.string.unknown_error)
                    )
                } ?: CustomResult.Error(resourceProvider.getString(R.string.unknown_error))
            }
            is Resource.Error -> {
                CustomResult.Error(loginResource.uiText.toString())
            }
        }
    }
}