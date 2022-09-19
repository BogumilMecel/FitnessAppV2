package com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.use_case

import android.text.format.DateUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.util.Resource
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.feature_log.domain.model.LogEntry

class CheckLatestLogEntry(
    private val insertLogEntry: InsertLogEntry
) {

    suspend operator fun invoke(
        logEntry: LogEntry
    ):Resource<LogEntry>{
        return if (!DateUtils.isToday(logEntry.timestamp)){
            insertLogEntry(System.currentTimeMillis())
        }else{
            Resource.Success(data = null)
        }
    }
}