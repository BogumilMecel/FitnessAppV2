package com.gmail.bogumilmecel2.fitnessappv2.common

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.OfflineMode
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsQuestion
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import kotlinx.datetime.LocalDate

class MockCachedValuesProvider : CachedValuesProvider {
    override suspend fun getWantedNutritionValues() = NutritionValues()
    override suspend fun saveWantedNutritionValues(nutritionValues: NutritionValues) {}
    override suspend fun getWeightProgress() = null
    override suspend fun updateWeightInfo(
        weightProgress: Double?,
        latestWeightEntry: WeightEntry?
    ) {}
    override suspend fun getLogStreak() = 1
    override suspend fun getLatestWeightEntry() = null
    override suspend fun getUser(): User = User()
    override suspend fun getUserCurrency() = Currency.PLN
    override suspend fun saveUser(user: User) {}
    override suspend fun getUserId() = ""
    override suspend fun updateAskForWeightDaily(accepted: Boolean) {}
    override suspend fun updateLocalWeightDialogsQuestion(weightDialogsQuestion: WeightDialogsQuestion) {}
    override suspend fun getLocalWeightDialogsQuestion(): WeightDialogsQuestion? = null
    override suspend fun getLocalLastTimeShowedWeightPicker() = null
    override suspend fun setLocalLastTimeShowedWeightPicker(date: LocalDate) {}
    override suspend fun updateUserInformation(userInformation: UserInformation) {}
    override suspend fun setOfflineMode(offlineMode: OfflineMode) {}
    override suspend fun getOfflineMode(): OfflineMode = OfflineMode.Online
}