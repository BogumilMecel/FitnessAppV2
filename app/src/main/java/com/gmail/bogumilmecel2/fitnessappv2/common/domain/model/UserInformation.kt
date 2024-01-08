package com.gmail.bogumilmecel2.fitnessappv2.common.domain.model

import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.ActivityLevel
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.DesiredWeight
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.Gender
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.TrainingFrequency
import com.gmail.bogumilmecel2.fitnessappv2.feature_introduction.domain.model.TypeOfWork
import kotlinx.serialization.Serializable

@Serializable
data class UserInformation (
    val activityLevel: ActivityLevel = ActivityLevel.LOW,
    val typeOfWork: TypeOfWork = TypeOfWork.SEDENTARY,
    val trainingFrequency: TrainingFrequency = TrainingFrequency.LOW,
    val gender: Gender = Gender.MALE,
    val height: Int = 0,
    val currentWeight: Double = 0.0,
    val desiredWeight: DesiredWeight = DesiredWeight.KEEP,
    val age: Int = 0
)