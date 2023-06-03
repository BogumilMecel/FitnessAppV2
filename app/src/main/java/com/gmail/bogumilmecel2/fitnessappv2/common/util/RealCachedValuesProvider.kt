package com.gmail.bogumilmecel2.fitnessappv2.common.util

import android.content.SharedPreferences
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.CachedValuesProvider
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.google.gson.Gson

class RealCachedValuesProvider(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
): CachedValuesProvider {
    override fun getWantedNutritionValues(): NutritionValues {
        TODO("Not yet implemented")
    }

    override fun saveWantedNutritionValues(nutritionValues: NutritionValues) {
        TODO("Not yet implemented")
    }

    override fun getWeightProgress(): String {
        TODO("Not yet implemented")
    }

    override fun updateWeightIProgress() {
        TODO("Not yet implemented")
    }

    override fun getLatestLogEntry(): LogEntry {
        TODO("Not yet implemented")
    }

    override fun getLatestWeightEntry(): WeightEntry {
        TODO("Not yet implemented")
    }

    override fun updateLatestWeightEntry() {
        TODO("Not yet implemented")
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