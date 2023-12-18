package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.NutritionValues
import com.gmail.bogumilmecel2.fitnessappv2.common.domain.model.UserInformation
import kotlinx.serialization.Serializable

@Serializable
data class IntroductionResponse(
    val nutritionValues: NutritionValues,
    val userInformation: UserInformation
)
