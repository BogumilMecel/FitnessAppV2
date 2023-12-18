package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.content.SharedPreferences
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.Currency
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.google.gson.Gson

class RealCachedValuesProvider(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) : CachedValuesProvider {

    companion object {
        const val USER_KEY = "user"
        const val CURRENCY = "currency"
    }

    override suspend fun getWantedNutritionValues() =
        getUser()?.nutritionValues ?: NutritionValues()

    override suspend fun saveWantedNutritionValues(nutritionValues: NutritionValues) {
        getUser()?.copy(
            nutritionValues = nutritionValues
        )?.let {
            saveUser(it)
        }
    }

    override suspend fun getWeightProgress() = getUser()?.weightProgress ?: ""

    override suspend fun updateWeightInfo(
        weightProgress: String?,
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

    override suspend fun getLatestLogEntry() = getUser()?.latestLogEntry ?: LogEntry()

    override suspend fun getLatestWeightEntry() = getUser()?.latestWeightEntry

    override suspend fun getUser() = getItemFromJson(
        USER_KEY,
        User::class.java
    )

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