package com.gmail.bogumilmecel2.fitnessappv2.feature_auth.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import com.gmail.bogumilmecel2.fitnessappv2.feature_weight.domain.model.WeightEntry
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("id")
    val id: String? = null,

    @SerialName("email")
    val email: String? = null,

    @SerialName("username")
    val username: String? = null,

    @SerialName("nutrition_values")
    val nutritionValues: NutritionValues? = null,

    @SerialName("user_information")
    val userInformation: UserInformation? = null,

    @SerialName("log_streak")
    val logStreak: Int? = null,

    @SerialName("latest_weight_entry")
    val latestWeightEntry: WeightEntry? = null,

    @SerialName("weight_progress")
    val weightProgress: Double? = null,

    @SerialName("ask_for_weight_daily")
    val askForWeightDaily: Boolean? = null,
)