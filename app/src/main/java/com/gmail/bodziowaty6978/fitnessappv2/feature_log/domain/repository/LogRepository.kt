package com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.repository

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.model.LogEntry

interface LogRepository {

    suspend fun saveLogEntry(logEntry: LogEntry, token: String): Resource<Boolean>

    suspend fun getLatestLogEntry(token: String):Resource<LogEntry>
}