package com.gmail.bogumilmecel2.fitnessappv2.common

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightDialogs
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry

class MockCachedValuesProvider : CachedValuesProvider {
    override suspend fun getWantedNutritionValues() = NutritionValues()
    override suspend fun saveWantedNutritionValues(nutritionValues: NutritionValues) {}
    override suspend fun getWeightProgress() = ""
    override suspend fun updateWeightInfo(
        weightProgress: String?,
        latestWeightEntry: WeightEntry?
    ) {}
    override suspend fun getLogStreak() = 1
    override suspend fun getLatestWeightEntry() = null
    override suspend fun getUser(): User = User()
    override suspend fun getUserCurrency() = Currency.PLN
    override suspend fun saveUser(user: User) {}
    override suspend fun getUserId() = ""
    override suspend fun updateWeightDialogs(weightDialogs: WeightDialogs) {}
    override suspend fun updateLocalLastTimeAskedForWeightDialogs(date: String) {}
    override suspend fun getLocalLastTimeAskedForWeightDialogs() = null
    override suspend fun getLocalLastTimeShowedWeightPicker() = null
    override suspend fun setLocalLastTimeShowedWeightPicker(date: String) {}
    override suspend fun updateUserInformation(userInformation: UserInformation) {}
}