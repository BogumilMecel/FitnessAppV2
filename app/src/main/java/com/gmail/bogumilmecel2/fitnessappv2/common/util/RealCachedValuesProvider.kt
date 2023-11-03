package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.content.SharedPreferences
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.OfflineMode
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.provider.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.WeightDialogsQuestion
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.google.gson.Gson

class RealCachedValuesProvider(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) : CachedValuesProvider {

    companion object {
        const val USER_KEY = "user"
        const val CURRENCY = "currency"
        const val WEIGHT_DIALOGS = "weight_dialogs"
        const val WEIGHT_PICKER = "weight_picker"
        const val OFFLINE_MODE = "offline_mode"
    }

    override suspend fun getWantedNutritionValues() =
        getUser()?.nutritionValues ?: NutritionValues()

    override suspend fun saveWantedNutritionValues(nutritionValues: NutritionValues) {
        getUser()?.let { saveUser(user = it.copy(nutritionValues = nutritionValues)) }
    }

    override suspend fun getWeightProgress() = getUser()?.weightProgress

    override suspend fun updateWeightInfo(
        weightProgress: Double?,
        latestWeightEntry: WeightEntry?
    ) {
        getUser()?.let {
            saveUser(
                user = it.copy(
                    latestWeightEntry = latestWeightEntry,
                    weightProgress = weightProgress
                )
            )
        }
    }

    override suspend fun getLogStreak() = getUser()?.logStreak ?: 1
    override suspend fun getLatestWeightEntry() = getUser()?.latestWeightEntry

    override suspend fun getUser() = getItemFromJson(
        USER_KEY,
        User::class.java
    )

    override suspend fun getUserId() = getUser()?.id ?: ""

    override suspend fun getUserCurrency(): Currency = getItemFromJson(
        key = CURRENCY,
        String::class.java
    )?.let {
        Currency.getCurrencyFromShortName(shortName = it)
    } ?: Currency.PLN

    override suspend fun saveUser(user: User) {
        saveItemToJson(
            item = user,
            key = USER_KEY
        )
    }

    override suspend fun updateAskForWeightDaily(accepted: Boolean) {
        getUser()?.let {
            saveUser(user = it.copy(askForWeightDaily = accepted))
        }
    }

    override suspend fun updateLocalWeightDialogsQuestion(weightDialogsQuestion: WeightDialogsQuestion) {
        saveItemToJson(
            item = weightDialogsQuestion,
            key = WEIGHT_DIALOGS
        )
    }

    override suspend fun getLocalWeightDialogsQuestion() = getItemFromJson(
        key = WEIGHT_DIALOGS,
        clazz = WeightDialogsQuestion::class.java
    )

    override suspend fun getLocalLastTimeShowedWeightPicker() = getItemFromJson(
        key = WEIGHT_PICKER,
        clazz = String::class.java
    )

    override suspend fun setLocalLastTimeShowedWeightPicker(date: String) {
        saveItemToJson(
            item = date,
            key = WEIGHT_PICKER
        )
    }

    override suspend fun updateUserInformation(userInformation: UserInformation) {
        getUser()?.let { saveUser(user = it.copy(userInformation = userInformation)) }
    }

    override suspend fun setOfflineMode(offlineMode: OfflineMode) {
        saveItemToJson(
            item = offlineMode,
            key = OFFLINE_MODE
        )
    }

    override suspend fun getOfflineMode(): OfflineMode = getItemFromJson(
        key = OFFLINE_MODE,
        clazz = OfflineMode::class.java
    ) ?: OfflineMode.Offline

    private fun <T> getItemFromJson(key: String, clazz: Class<T>): T? {
        return try {
            sharedPreferences.getString(
                key,
                null
            )?.let { jsonString ->
                gson.fromJson(
                    jsonString,
                    clazz
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun saveItemToJson(item: Any, key: String): Boolean {
        return try {
            gson.toJson(item)?.let { jsonString ->
                sharedPreferences.edit().putString(
                    key,
                    jsonString
                ).commit()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}