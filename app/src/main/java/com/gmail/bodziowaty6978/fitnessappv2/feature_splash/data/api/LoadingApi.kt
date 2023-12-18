package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api

import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User
import retrofit2.http.GET

interface LoadingApi {

    @GET("/authentication/authenticate")
    suspend fun authenticate(): Boolean

    @GET("/userData/user/")
    suspend fun getUser(): User?
}