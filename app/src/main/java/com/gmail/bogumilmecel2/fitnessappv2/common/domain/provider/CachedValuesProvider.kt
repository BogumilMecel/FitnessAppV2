package com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsQuestion
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry

interface CachedValuesProvider {
    suspend fun getWantedNutritionValues(): NutritionValues
    suspend fun saveWantedNutritionValues(nutritionValues: NutritionValues)
    suspend fun getWeightProgress(): Double?
    suspend fun updateWeightInfo(
        weightProgress: Double?,
        latestWeightEntry: WeightEntry?
    )
    suspend fun getLogStreak(): Int
    suspend fun getLatestWeightEntry(): WeightEntry?
    suspend fun getUser(): User?
    suspend fun getUserCurrency(): Currency
    suspend fun saveUser(user: User)
    suspend fun getUserId(): String
    suspend fun updateAskForWeightDaily(accepted: Boolean)
    suspend fun updateLocalWeightDialogsQuestion(weightDialogsQuestion: WeightDialogsQuestion)
    suspend fun getLocalWeightDialogsQuestion(): WeightDialogsQuestion?
    suspend fun getLocalLastTimeShowedWeightPicker(): String?
    suspend fun setLocalLastTimeShowedWeightPicker(date: String)
    suspend fun updateUserInformation(userInformation: UserInformation)
}