package com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry

interface CachedValuesProvider {
    suspend fun getWantedNutritionValues(): NutritionValues
    suspend fun saveWantedNutritionValues(nutritionValues: NutritionValues)
    suspend fun getWeightProgress(): String
    suspend fun updateWeightInfo(
        weightProgress: String?,
        latestWeightEntry: WeightEntry?
    )
    suspend fun getLatestLogEntry(): LogEntry
    suspend fun getLatestWeightEntry(): WeightEntry?
    suspend fun getUser(): User?
    suspend fun getUserCurrency(): Currency
    suspend fun saveUser(user: User)
}