package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightDialogs
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String = "",
    val email: String = "",
    val username:String = "",
    val nutritionValues: NutritionValues? = null,
    val userInformation: UserInformation? = null,
    val logStreak: Int = 1,
    val latestWeightEntry: WeightEntry? = null,
    val weightProgress: Double? = null,
    val weightDialogs: WeightDialogs? = null
)