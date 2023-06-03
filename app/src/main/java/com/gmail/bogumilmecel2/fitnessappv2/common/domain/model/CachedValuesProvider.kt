package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry

interface CachedValuesProvider {
    fun getWantedNutritionValues(): NutritionValues
    fun saveWantedNutritionValues(nutritionValues: NutritionValues)
    fun getWeightProgress(): String
    fun updateWeightIProgress()
    fun getLatestLogEntry(): LogEntry
    fun getLatestWeightEntry(): WeightEntry
    fun updateLatestWeightEntry()
}