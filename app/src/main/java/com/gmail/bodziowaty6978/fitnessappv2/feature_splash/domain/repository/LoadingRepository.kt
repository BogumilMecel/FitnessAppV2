package com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.CustomResult
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogRequest

interface LoadingRepository {
    suspend fun authenticateUser(): CustomResult
    suspend fun getUser(): Resource<User?>
    suspend fun addLogEntry(logRequest: LogRequest): Resource<LogEntry>
}