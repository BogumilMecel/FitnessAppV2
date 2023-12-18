package com.gmail.bodziowaty6978.fitnessappv2.feature_log.data.api

import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.model.LogRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LogApi {

    @GET("/logEntries/latest")
    suspend fun getLatestLogEntry(
        @Header("Authorization") token:String
    ): LogEntry?

    @POST("/logEntries")
    suspend fun postLogEntry(
        @Body logRequest: LogRequest,
        @Header("Authorization") token:String,
    ): LogEntry
}