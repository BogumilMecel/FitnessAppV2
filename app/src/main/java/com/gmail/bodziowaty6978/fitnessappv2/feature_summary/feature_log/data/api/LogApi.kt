package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.data.api

import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.model.LogEntry
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface LogApi {

    @GET("logEntries/latest")
    suspend fun getLatestLogEntry(
        @Header("Authorization") token:String
    ): LogEntry

    @POST("logEntries")
    suspend fun postLogEntry(
        @Query("timestamp") timestamp: Long,
        @Header("Authorization") token:String
    ): LogEntry
}