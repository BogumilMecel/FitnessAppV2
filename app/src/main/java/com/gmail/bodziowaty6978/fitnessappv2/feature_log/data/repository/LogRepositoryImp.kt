package com.gmail.bodziowaty6978.fitnessappv2.feature_log.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.data.api.LogApi
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.model.LogRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_log.domain.repository.LogRepository
import retrofit2.HttpException

class LogRepositoryImp(
    private val resourceProvider: ResourceProvider,
    private val logApi: LogApi
) : LogRepository {

    override suspend fun saveLogEntry(timestamp: Long, token: String): Resource<LogEntry> {
        return try {
            val logEntry = logApi.postLogEntry(
                logRequest = LogRequest(timestamp = timestamp),
                token = token
            )
            Resource.Success(logEntry)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }

    override suspend fun getLatestLogEntry(token: String): Resource<LogEntry> {
        return try {
            val logEntry = logApi.getLatestLogEntry(
                token = token
            )
            logEntry?.let {
                Resource.Success(data = it)
            } ?: saveLogEntry(
                timestamp = System.currentTimeMillis(),
                token = token
            )
        } catch (e: Exception) {
            if (e is HttpException && e.code() == 404) {
                saveLogEntry(
                    timestamp = System.currentTimeMillis(),
                    token = token
                )
            } else {
                e.printStackTrace()
                Resource.Error(resourceProvider.getString(R.string.unknown_error))
            }
        }
    }
}