package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_splash.domain.repository.LoadingRepository
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogRequest

class GetLatestLogEntry(
    private val loadingRepository: LoadingRepository,
) {
    suspend operator fun invoke(logRequest: LogRequest): Resource<LogEntry> {
        return loadingRepository.addLogEntry(logRequest)
    }
}