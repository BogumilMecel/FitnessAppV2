package com.gmail.bodziowaty6978.fitnessappv2.common.data.utils

import android.content.SharedPreferences
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.google.gson.Gson

class CustomSharedPreferencesUtils(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) {
    fun saveWantedNutritionValues(wantedNutritionValues: NutritionValues): Boolean{
        return saveItemToJson(wantedNutritionValues, SharedPreferencesKeys.WANTED_NUTRITION_VALUES_KEY)
    }

    fun getWantedNutritionValues(): NutritionValues? {
        return getItemFromJson(SharedPreferencesKeys.WANTED_NUTRITION_VALUES_KEY, NutritionValues::class.java)
    }

    fun saveUserInformation(userInformation: UserInformation): Boolean{
        return saveItemToJson(userInformation, SharedPreferencesKeys.USER_INFORMATION_KEY)
    }

    fun getUserInformation(): UserInformation? {
        return getItemFromJson(SharedPreferencesKeys.USER_INFORMATION_KEY, UserInformation::class.java)
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