package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.domain.repository

import com.gmail.bogumilmecel2.fitnessappv2.common.util.Resource
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User

interface LoadingRepository {
    suspend fun authenticateUser(): Resource<User?>
}