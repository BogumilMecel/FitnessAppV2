package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry

interface CachedValuesProvider {
    suspend fun getWantedNutritionValues(): NutritionValues
    suspend fun saveWantedNutritionValues(nutritionValues: NutritionValues)
    suspend fun getWeightProgress(): String
    suspend fun updateWeightIProgress()
    suspend fun getLatestLogEntry(): LogEntry
    suspend fun getLatestWeightEntry(): WeightEntry
    suspend fun updateLatestWeightEntry()
}