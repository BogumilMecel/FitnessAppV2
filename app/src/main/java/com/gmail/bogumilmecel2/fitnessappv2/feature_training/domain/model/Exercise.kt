package com.gmail.bogumilmecel2.fitnessappv2.feature_training.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Exercise(
    @SerialName("name")
    val name: String,

    @SerialName("exercise_type")
    val exerciseType: ExerciseType = ExerciseType.WeightAndReps,

    @SerialName("categories")
    val categories: List<ExerciseCategory> = emptyList(),

    @SerialName("note")
    val note: String? = null
)