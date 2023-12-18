package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.repository.LogRepository

class GetLatestLogEntry(
    private val logRepository: LogRepository,
    private val getToken: GetToken,
    private val resourceProvider: ResourceProvider
) {

    suspend operator fun invoke(): Resource<LogEntry> {
        val token = getToken()

        return token?.let {
            logRepository.getLatestLogEntry(
                token = it
            )
        } ?: Resource.Error(resourceProvider.getString(R.string.unknown_error))
    }
}