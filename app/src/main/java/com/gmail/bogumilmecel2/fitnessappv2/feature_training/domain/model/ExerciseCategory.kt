package com.gmail.bogumilmecel2.fitnessappv2.feature_training.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ExerciseCategory {
    @SerialName("abs")
    Abs,

    @SerialName("back")
    Back,

    @SerialName("biceps")
    Biceps,

    @SerialName("cardio")
    Cardio,

    @SerialName("chest")
    Chest,

    @SerialName("triceps")
    Triceps,

    @SerialName("shoulders")
    Shoulders,

    @SerialName("legs")
    Legs,

    @SerialName("quads")
    Quads,

    @SerialName("hamstrings")
    Hamstrings,

    @SerialName("glutes")
    Glutes
}