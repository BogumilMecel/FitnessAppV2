package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.model.LogEntry

interface LogRepository {

    suspend fun saveLogEntry(timestamp: Long, token: String): Resource<LogEntry>

    suspend fun getLatestLogEntry(token: String):Resource<LogEntry>
}