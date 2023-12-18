package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.data.api.LogApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.repository.LogRepository

class LogRepositoryImp(
    private val resourceProvider: ResourceProvider,
    private val logApi: LogApi
): LogRepository {

    override suspend fun saveLogEntry(timestamp: Long, token: String): Resource<LogEntry> {
        return try {
            val logEntry = logApi.postLogEntry(
                timestamp = timestamp,
                token = token
            )
            Resource.Success(logEntry)
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }

    }

    override suspend fun getLatestLogEntry(token: String): Resource<LogEntry> {
        return try {
            Resource.Success(
                data = logApi.getLatestLogEntry(
                    token = token
                )
            )
        }catch (e:Exception){
            e.printStackTrace()
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }
}