package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.content.SharedPreferences
import com.gmail.bogumilmecel2.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.google.gson.Gson

class RealCachedValuesProvider(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
): CachedValuesProvider {

    companion object {
        const val USER_KEY = "user"
    }

    override suspend fun getWantedNutritionValues(): NutritionValues {
        TODO("Not yet implemented")
    }

    override suspend fun saveWantedNutritionValues(nutritionValues: NutritionValues) {
        TODO("Not yet implemented")
    }

    override suspend fun getWeightProgress(): String {
        TODO("Not yet implemented")
    }

    override suspend fun updateWeightIProgress() {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestLogEntry(): LogEntry {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestWeightEntry(): WeightEntry {
        TODO("Not yet implemented")
    }

    override suspend fun updateLatestWeightEntry() {
        TODO("Not yet implemented")
    }

    private suspend fun getUser() = getItemFromJson(CustomSharedPreferencesUtils.USER_KEY, User::class.java)

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