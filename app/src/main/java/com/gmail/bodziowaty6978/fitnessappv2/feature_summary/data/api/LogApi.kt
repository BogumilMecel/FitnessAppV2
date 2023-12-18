package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.data.api

import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface LogApi {
    @POST("/logEntries/latest/")
    suspend fun getLatestLogEntry(@Body logRequest: LogRequest): LogEntry
}