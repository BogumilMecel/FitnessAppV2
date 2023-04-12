package com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class IntroductionRequest(
    val gender: Gender,
    val weight: Double,
    val age: Int,
    val height: Int,
    val activityLevel: ActivityLevel,
    val trainingFrequency: TrainingFrequency,
    val typeOfWork: TypeOfWork,
    val desiredWeight: DesiredWeight
)
