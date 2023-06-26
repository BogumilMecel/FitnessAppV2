package com.gmail.bogumilmecel2.fitnessappv2.feature_splash.data.api

import com.gmail.bogumilmecel2.fitnessappv2.common.util.ApiConstants
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import retrofit2.http.Header
import retrofit2.http.POST

interface LoadingApi {
    @POST("/authentication/authenticate")
    suspend fun authenticate(@Header(ApiConstants.Headers.TIMEZONE) timezone: String): User?
}