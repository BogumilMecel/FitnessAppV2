package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.feature_summary.domain.model.LogEntry
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry

@kotlinx.serialization.Serializable
data class User(
    val id: String = "",
    val email: String = "",
    val username:String = "",
    val nutritionValues: NutritionValues? = null,
    val userInformation: UserInformation? = null,
    val favoriteUserRecipesIds: List<String>?,
    val latestLogEntry: LogEntry? = null,
    val latestWeightEntry: WeightEntry? = null,
    val weightProgress: String? = null
)