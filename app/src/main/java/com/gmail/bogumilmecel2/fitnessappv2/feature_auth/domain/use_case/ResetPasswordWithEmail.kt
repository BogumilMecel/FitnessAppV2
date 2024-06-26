package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.use_case

import android.util.Patterns
import com.gmail.bogumilmecel2.fitnessappv2.R
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.ResourceProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.repository.AuthRepository

class ResetPasswordWithEmail(
    private val repository: AuthRepository,
    private val resourceProvider: ResourceProvider
){

    suspend operator fun invoke(email: String): Resource<Boolean> {
        if (email.isBlank()){
            return Resource.Error(resourceProvider.getString(R.string.please_enter_your_email_in_order_to_reset_your_password))
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return Resource.Error(resourceProvider.getString(R.string.please_make_sure_you_have_entered_correct_email))
        }
        return repository.sendPasswordResetEmail(email)
    }
}