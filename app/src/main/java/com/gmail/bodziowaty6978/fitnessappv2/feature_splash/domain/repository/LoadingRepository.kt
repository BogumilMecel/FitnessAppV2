package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource

interface LoadingRepository {

    suspend fun authenticateUser(
        token:String
    ):Resource<Boolean>

    suspend fun getToken():Resource<String>
}