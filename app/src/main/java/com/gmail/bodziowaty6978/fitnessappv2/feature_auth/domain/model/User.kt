package com.gmail.bodziowaty6978.fitnessappv2.feature_auth.domain.model

import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bodziowaty6978.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bodziowaty6978.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bodziowaty6978.fitnessappv2.feature_weight.domain.model.WeightEntry

@kotlinx.serialization.Serializable
data class User(
    val id: String = "",
    val email: String = "",
    val username:String = "",
    val nutritionValues: NutritionValues? = null,
    val userInformation: UserInformation? = null,
    val latestLogEntry: LogEntry? = null,
    val weightEntries: List<WeightEntry> = emptyList()
)
