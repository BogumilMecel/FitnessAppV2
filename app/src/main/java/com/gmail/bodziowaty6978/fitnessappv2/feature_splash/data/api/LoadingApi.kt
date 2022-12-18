package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.data.api

import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoadingApi {
    @GET("/authentication/authenticate")
    suspend fun authenticate(): Boolean
    @GET("/userData/user/")
    suspend fun getUser(): User?

    @POST("/logEntries/latest/")
    suspend fun addLogEntry(
        @Body logRequest: LogRequest
    ): LogEntry

}