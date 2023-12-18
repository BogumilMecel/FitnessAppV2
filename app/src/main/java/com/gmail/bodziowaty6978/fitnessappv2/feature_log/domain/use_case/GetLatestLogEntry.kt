package com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.repository.LogRepository

class GetLatestLogEntry(
    private val logRepository: LogRepository,
) {

    suspend operator fun invoke(): Resource<LogEntry> {
        return logRepository.getLatestLogEntry()
    }
}