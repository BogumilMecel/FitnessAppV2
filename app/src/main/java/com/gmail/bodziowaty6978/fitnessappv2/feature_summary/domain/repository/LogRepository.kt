package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogRequest

interface LogRepository {
    suspend fun getLatestLogEntry(logRequest: LogRequest):Resource<LogEntry>
}