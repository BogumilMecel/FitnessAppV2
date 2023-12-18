package com.gmail.bodziowaty6978.fitnessappv2

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.gmail.bodziowaty6978.fitnessappv2.common.data.utils.CustomSharedPreferencesUtils
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model.User
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class FitnessApp:Application() {
    companion object{

        private lateinit var sharedPreferencesUtils: CustomSharedPreferencesUtils

        private fun setSharedPreferencesUtils(sharedPreferences: SharedPreferences){
            sharedPreferencesUtils = CustomSharedPreferencesUtils(
                sharedPreferences = sharedPreferences,
                gson = Gson()
            )
        }
        fun getWantedNutritionValues() = sharedPreferencesUtils.getUser()?.nutritionValues ?: NutritionValues()
        fun saveWantedNutritionValues(nutritionValues: NutritionValues) = sharedPreferencesUtils.saveWantedNutritionValues(nutritionValues)
        fun saveUserInformation(userInformation: UserInformation) = sharedPreferencesUtils.saveUserInformation(userInformation)
        fun getUsername(): String = sharedPreferencesUtils.getUser()?.username ?: ""
        fun getUserId(): String = sharedPreferencesUtils.getUser()?.id ?: ""
        fun getLatestLogEntry(): LogEntry = sharedPreferencesUtils.getUser()?.latestLogEntry ?: LogEntry()
        fun updateLatestLogEntry(logEntry: LogEntry): Boolean = sharedPreferencesUtils.updateLatestLogEntry(logEntry = logEntry)
        fun getWeightEntries(): List<WeightEntry> = sharedPreferencesUtils.getUser()?.weightEntries ?: emptyList()
        fun updateWeightEntries(weightEntry: WeightEntry): Boolean = sharedPreferencesUtils.updateLatestWeightEntries(weightEntry = weightEntry)
        fun saveUser(user: User) = sharedPreferencesUtils.saveUser(user = user)
    }

    override fun onCreate() {
        super.onCreate()
        setSharedPreferencesUtils(getSharedPreferences("fitness_app", Context.MODE_PRIVATE))
    }
}