package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.AuthenticationRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User

interface LoadingRepository {
    suspend fun authenticateUser(authenticationRequest: AuthenticationRequest): Resource<User?>
}