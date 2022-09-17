package com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.use_case

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.use_case.GetToken
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.repository.LogRepository

class InsertLogEntry(
    private val logRepository: LogRepository,
    private val getToken: GetToken,
    private val resourceProvider: ResourceProvider
) {

    suspend operator fun invoke(logEntry: LogEntry): Resource<Boolean> {
        val token = getToken()

        return token?.let {
            logRepository.saveLogEntry(
                token = it,
                logEntry = logEntry
            )
        } ?: Resource.Error(resourceProvider.getString(R.string.unknown_error))


    }
}