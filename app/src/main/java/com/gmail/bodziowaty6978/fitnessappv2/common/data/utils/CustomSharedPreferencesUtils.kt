package com.gmail.bodziowaty6978.fitnessappv2.common.data.utils

import android.content.Context
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.NewWeightEntryResponse
import com.google.gson.Gson

class CustomSharedPreferencesUtils(
    context: Context,
    private val gson: Gson
) {
    companion object{
        const val USER_KEY = "user"
    }

    private val sharedPreferences = context.getSharedPreferences("fitness_app", Context.MODE_PRIVATE)

    fun updateWeightInfo(newWeightEntryResponse: NewWeightEntryResponse): Boolean{
        return getUser()?.let {
            saveUser(
                user = it.copy(
                    latestWeightEntry = newWeightEntryResponse.latestWeightEntry,
                    weightProgress = newWeightEntryResponse.weightProgress
                )
            )
        } ?: false
    }

    fun getWantedNutritionValues() = getUser()?.nutritionValues ?: NutritionValues()

    fun saveWantedNutritionValues(wantedNutritionValues: NutritionValues): Boolean{
        return getUser()?.copy(
            nutritionValues = wantedNutritionValues
        )?.let {
            saveItemToJson(it, USER_KEY)
            true
        } ?: false
    }
    fun getUserId() = getUser()?.id ?: ""
    fun getUsername() = getUser()?.username ?: ""
    fun getFavoriteRecipesIds() = getUser()?.favoriteUserRecipesIds ?: emptyList()
    fun getLatestWeightEntry() = getUser()?.latestWeightEntry
    fun getWeightProgress() = getUser()?.weightProgress
    fun getLatestLogEntry() = getUser()?.latestLogEntry ?: LogEntry()

    private fun getUser(): User? {
        return getItemFromJson(USER_KEY, User::class.java)
    }

    fun saveUser(user: User): Boolean {
        return saveItemToJson(user, USER_KEY)
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