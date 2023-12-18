package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.repository.LogRepository

class GetLatestLogEntry(
    private val logRepository: LogRepository,
) {
    suspend operator fun invoke(logRequest: LogRequest): Resource<LogEntry> {
        return logRepository.getLatestLogEntry(logRequest)
    }
}