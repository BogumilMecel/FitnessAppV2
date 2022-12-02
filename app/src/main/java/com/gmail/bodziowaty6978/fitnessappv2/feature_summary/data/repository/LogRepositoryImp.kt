package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.data.repository

import com.gmail.bodziowaty6978.fitnessappv2.R
import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.common.util.ResourceProvider
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogRequest
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.repository.LogRepository

class LogRepositoryImp(
    private val resourceProvider: ResourceProvider,
    private val customSharedPreferencesUtils: CustomSharedPreferencesUtils
) : LogRepository {
    override suspend fun getLatestLogEntry(logRequest: LogRequest): Resource<LogEntry> {
        return try {
            Resource.Success(
                data = customSharedPreferencesUtils.getLatestLogEntry()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(resourceProvider.getString(R.string.unknown_error))
        }
    }
}