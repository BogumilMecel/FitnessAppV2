package com.gmail.bodziowaty6978.fitnessappv2.common.data.utils

import android.content.SharedPreferences
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.google.gson.Gson

class CustomSharedPreferencesUtils(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {
    fun updateLatestLogEntry(logEntry: LogEntry): Boolean {
        return getUser()?.copy(
            latestLogEntry = logEntry
        )?.let {
            saveItemToJson(it, SharedPreferencesKeys.USER_KEY)
            true
        } ?: false
    }

    fun updateLatestWeightEntries(weightEntry: WeightEntry): Boolean{
        return getUser()?.let {
            saveUser(
                user = it.copy(
                    weightEntries = it.weightEntries.toMutableList().apply {
                        add(weightEntry)
                    }
                )
            )
        } ?: false
    }

    fun saveWantedNutritionValues(wantedNutritionValues: NutritionValues): Boolean{
        return getUser()?.copy(
            nutritionValues = wantedNutritionValues
        )?.let {
            saveItemToJson(it, SharedPreferencesKeys.USER_KEY)
            true
        } ?: false
    }

    fun saveUserInformation(userInformation: UserInformation): Boolean{
        return getUser()?.copy(
            userInformation = userInformation
        )?.let {
            saveItemToJson(it, SharedPreferencesKeys.USER_KEY)
            true
        } ?: false
    }

    fun getUser(): User? {
        return getItemFromJson(SharedPreferencesKeys.USER_KEY, User::class.java)
    }

    fun saveUser(user: User): Boolean {
        return saveItemToJson(user, SharedPreferencesKeys.USER_KEY)
    }

    private fun <T> getItemFromJson(key: String, clazz: Class<T>): T?{
        return try {
            sharedPreferences.getString(key, null)?.let { jsonString ->
                gson.fromJson(jsonString, clazz)
            }
        }catch (e:Exception){
            e.printStackTrace()
            null
        }
    }

    private fun saveItemToJson(item: Any, key: String): Boolean {
        return try {
            gson.toJson(item)?.let { jsonString ->
                sharedPreferences.edit().putString(key, jsonString).commit()
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}